#!/bin/bash
# Microservice endpoint calls for test patient assessments :

curl -d "patId=1" -X POST http://localhost:8083/assess/id
curl -d "patId=2" -X POST http://localhost:8083/assess/id
curl -d "patId=3" -X POST http://localhost:8083/assess/id
curl -d "patId=4" -X POST http://localhost:8083/assess/id

curl -d "familyName=TestNone" -X POST http://localhost:8083/assess/familyName
curl -d "familyName=TestBorderline" -X POST http://localhost:8083/assess/familyName
curl -d "familyName=TestInDanger" -X POST http://localhost:8083/assess/familyName
curl -d "familyName=TestEarlyOnset" -X POST http://localhost:8083/assess/familyName