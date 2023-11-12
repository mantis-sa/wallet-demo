#!/bin/bash

# Generate a random UUID for userId
USER_ID=$(uuidgen)

# Generate a random UUID for add funds transactionId
ADD_FUNDS_TRANSACTION_ID=$(uuidgen)

# Generate a random UUID for remove funds transactionId
REMOVE_FUNDS_TRANSACTION_ID=$(uuidgen)

# Creates a wallet for the userID
WALLET_ID=$(curl -X POST http://localhost:8080/wallets/user/$USER_ID)

# Get the Wallet summary
curl -H "userID: $USER_ID" http://localhost:8080/wallets/$WALLET_ID/summary

ADD_FUNDS_PAYLOAD=$(jq -n --arg tx_id "$ADD_FUNDS_TRANSACTION_ID" '{"transactionId": $tx_id, "funds": "50.01"}')
REMOVE_FUNDS_PAYLOAD=$(jq -n --arg tx_id "$REMOVE_FUNDS_TRANSACTION_ID" '{"transactionId": $tx_id, "funds": "20.13"}')
ANOTHER_REMOVE_FUNDS_PAYLOAD=$(jq -n --arg tx_id "$REMOVE_FUNDS_TRANSACTION_ID" '{"transactionId": $tx_id, "funds": "10.15"}')

# Executing the first addFunds transaction
curl -X PUT -H "userID: $USER_ID" -H "Content-Type: application/json" http://localhost:8080/wallets/$WALLET_ID/funds/add -d "$ADD_FUNDS_PAYLOAD"

# Confirm that it has been added
curl -H "userID: $USER_ID" http://localhost:8080/wallets/$WALLET_ID/summary

# Now remove the funds from the wallet
curl -X PUT -H "userID: $USER_ID" -H "Content-Type: application/json" http://localhost:8080/wallets/$WALLET_ID/funds/remove -d "$REMOVE_FUNDS_PAYLOAD"

# Confirm that it has been removed
curl -H "userID: $USER_ID" http://localhost:8080/wallets/$WALLET_ID/summary

# Try to remove again - should complain of duplicate transaction issue
curl -X PUT -H "userID: $USER_ID" -H "Content-Type: application/json" http://localhost:8080/wallets/$WALLET_ID/funds/remove -d "$ANOTHER_REMOVE_FUNDS_PAYLOAD"