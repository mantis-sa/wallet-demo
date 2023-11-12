#!/bin/bash

# Generate a random UUID
USER_ID=$(uuidgen)

# Creates a wallet for the userID
WALLET_ID=$(curl -X POST http://localhost:8080/wallets/user/$USER_ID)

RANDOM_USER_ID=$(uuidgen)

# Should throw an unauthorised because newly generated userID will not be associated with the wallet
curl -H "userID: $RANDOM_USER_ID" http://localhost:8080/wallets/$WALLET_ID/summary