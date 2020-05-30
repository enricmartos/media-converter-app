.PONY: all build test

up-and-run:
	@docker-compose -f docker-compose-debug.yaml up

up-and-run-with-build:
	@docker-compose -f docker-compose-debug.yaml up --build

build:
	@./gradlew build --warning-mode all

run-tests:
	@./gradlew test --warning-mode all

test:
	@docker exec mediaconverter_java_debug ./gradlew test --warning-mode all

run:
	@./gradlew bootRun

