#!/bin/bash

# Generate a random UUID
USER_ID=$(uuidgen)

# Creates a wallet for the userID
WALLET_ID=$(curl -X POST http://localhost:8080/wallets/user/$USER_ID)

# Get the Wallet summary
curl -H "userID: $USER_ID" http://localhost:8080/wallets/$WALLET_ID/summary

echo "WalletSummary - USER_ID: $USER_ID, WALLET_ID: $WALLET_ID"