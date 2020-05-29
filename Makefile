.PONY: all build test

all: build

up:
	@docker-compose up -d

build:
	@./gradlew build --warning-mode all

run-tests:
	@./gradlew test --warning-mode all

test:
	@docker exec mediaconverter_java ./gradlew test --warning-mode all

run:
	@./gradlew bootRun

# Start the app
start-mooc_backend:
	@./gradlew :run --args='mooc_backend server'

