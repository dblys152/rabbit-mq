# rabbit-mq

### 로컬 테스트 RabbitMQ
    # 볼륨 생성 (최초)
    $ docker volume create rabbitmq_data

    docker run --name rabbitmq -d \
    -v rabbitmq_data:/var/lib/rabbitmq \
    -p 15672:15672 \
    -p 5672:5672 \
    -e RABBITMQ_DEFAULT_USER=ysrabbit \
    -e RABBITMQ_DEFAULT_PASS=ysrabbit \
    rabbitmq:3-management

