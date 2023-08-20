package com.ys.event_store.adapter.out.persistence.converter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
public class MapTypeHandler extends BaseTypeHandler<Map<String, Object>> {

    private final ObjectMapper objectMapper;

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, Map<String, Object> parameter, JdbcType jdbcType) throws SQLException {
        try {
            String jsonValue = objectMapper.writeValueAsString(parameter);
            ps.setString(i, jsonValue);
        } catch (JsonProcessingException e) {
            throw new SQLException("Error converting Map to String.", e);
        }
    }

    @Override
    public Map<String, Object> getNullableResult(ResultSet rs, String columnName) throws SQLException {
        return toMap(rs.getString(columnName));
    }

    @Override
    public Map<String, Object> getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        return toMap(rs.getString(columnIndex));
    }

    @Override
    public Map<String, Object> getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        return toMap(cs.getString(columnIndex));
    }

    private Map<String, Object> toMap(String value) {
        if (value == null) {
            return null;
        }

        try {
            return objectMapper.readValue(value.toString(), HashMap.class);
        } catch (Exception e) {
            throw new IllegalArgumentException("Error parsing Map data", e);
        }
    }
}
