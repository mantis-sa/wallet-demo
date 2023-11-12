#!/bin/bash

# Generate a random UUID
USER_ID=$(uuidgen)

# Creates a wallet for the userID
WALLET_ID=$(curl -X POST http://localhost:8080/wallets/user/$USER_ID)

RANDOM_USER_ID=$(uuidgen)

# Should throw an unauthorised because newly generated userID will not be associated with the wallet
curl -X PUT -H "userID: $RANDOM_USER_ID" -H "Content-Type: application/json" http://localhost:8080/wallets/$WALLET_ID/funds/add -d '{"funds":"50.01"}'