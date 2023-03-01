APP_IMAGE_LIST ?= gateway-service auth-service user-service reservation-service notification-service
docker image rm -f gateway-service auth-service user-service hotel-service reservation-service notification-service hotel_db user_db reservation_db kafka zookeeper redis
start : down remove up

down :
	docker compose down

remove :
	docker image rm -f ${APP_IMAGE_LIST}

up:
	docker compose up -d

build:
	docker compose build

restart: down up
