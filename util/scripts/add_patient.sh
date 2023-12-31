#!/bin/bash
# Microservice endpoint calls to set test patient demographics

curl -d "family=TestNone&given=Test&dob=1970-12-31&sex=F&address=1 Brookside St&phone=100-222-3333" -X POST http://localhost:8081/patient/add
curl -d "family=TestBorderline&given=Test&dob=1950-06-24&sex=M&address=2 High St&phone=200-333-4444" -X POST http://localhost:8081/patient/add
curl -d "family=TestInDanger&given=Test&dob=2009-06-18&sex=M&address=3 Club Road&phone=300-444-5555" -X POST http://localhost:8081/patient/add
curl -d "family=TestEarlyOnset&given=Test&dob=2007-06-28&sex=F&address=4 Valley Dr&phone=400-555-6666" -X POST http://localhost:8081/patient/add