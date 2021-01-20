# WalletManagement


REST application of Wallet management using Spring Boot and Spring Data. This application will perform basic CRUD(Create, Read , Update , Delete) operations on the Wallet table. The curl commands for APIs are:


// POST requests for Wallet


$curl -X POST -H "Content-Type: application/json" -d "

{"phoneNumber":"phone_number","WallBalance":"wall_balance"}" http://localhost:8080/wallet


//POST requests for transaction

$curl -X POST -H "Content-Type: application/json" -d "

{"payerPhone":"phone_number","payeePhone":"phone_number","txnAmount":"txn_Amount"}" http://localhost:8080/transaction



READ Wallet :-

$curl http://localhost:8080/user/wallets

READ Transaction  :-

$curl http://localhost:8080/user/transactions

READ by id:-

$curl http://localhost:8080/user/transaction/{id}
