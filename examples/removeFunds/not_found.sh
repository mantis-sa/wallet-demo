#!/bin/bash

# Generate a random UUID
USER_ID=$(uuidgen)

# Creates a wallet for the userID
_=$(curl -X POST http://localhost:8080/wallets/user/$USER_ID)

# Add funds for a randomly generated WalletID - will return a Bad Request (wallet can't be found).
RANDOM_WALLET_ID=$(uuidgen)
curl -X PUT -H "userID: $USER_ID" -H "Content-Type: application/json" http://localhost:8080/wallets/$RANDOM_WALLET_ID/funds/remove -d '{"funds":"50.01"}'