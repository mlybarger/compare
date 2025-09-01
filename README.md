
# needs postgresql running locally via:

docker run -d --name test-db -e POSTGRES_PASSWORD=changeme -p 5432:5432 postgres

# then you can run the tests via:

mvn verify -Denv=dev

# or

mvn verify -Denv=test


