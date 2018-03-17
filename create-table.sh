aws dynamodb create-table \
    --endpoint-url http://localhost:8000 \
    --table-name Komms \
    --attribute-definitions \
        AttributeName=author-date,AttributeType=S \
        AttributeName=tags,AttributeType=S \
    --key-schema \
                 AttributeName=author-date,KeyType=HASH \
                 AttributeName=tags,KeyType=GSI \
    --provisioned-throughput ReadCapacityUnits=1,WriteCapacityUnits=1
