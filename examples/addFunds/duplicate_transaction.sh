#!/bin/bash

# Generate a random UUID for userId
USER_ID=$(uuidgen)

# Generate a random UUID for transactionId
TRANSACTION_ID=$(uuidgen)

# Creates a wallet for the userID
WALLET_ID=$(curl -X POST http://localhost:8080/wallets/user/$USER_ID)

# Get the Wallet summary
curl -H "userID: $USER_ID" http://localhost:8080/wallets/$WALLET_ID/summary

PAYLOAD=$(jq -n --arg tx_id "$TRANSACTION_ID" '{"transactionId": $tx_id, "funds": "50.01"}')
SAME_TX_ID_PAYLOAD=$(jq -n --arg tx_id "$TRANSACTION_ID" '{"transactionId": $tx_id, "funds": "20.13"}')

# Executing the first addFunds transaction
curl -X PUT -H "userID: $USER_ID" -H "Content-Type: application/json" http://localhost:8080/wallets/$WALLET_ID/funds/add -d "$PAYLOAD"

# Confirm that it has been added
curl -H "userID: $USER_ID" http://localhost:8080/wallets/$WALLET_ID/summary

# Executing another transaction with the same transactionId - This should fail.
curl -X PUT -H "userID: $USER_ID" -H "Content-Type: application/json" http://localhost:8080/wallets/$WALLET_ID/funds/add -d "$SAME_TX_ID_PAYLOAD"