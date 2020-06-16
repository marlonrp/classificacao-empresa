# Backend

This project was generated with [Spring Initializr](https://start.spring.io/) version 2.3.0.

## Requirements

This project need Java version 11.

## Using API Rest

### Company service

    URL: http://localhost:8090/company
    Method allowed: GET
    Response:
    [
        {
            "id": 1,
            "name": "Company 1"
        },
        {
            "id": 2,
            "name": "Company 2"
        }
    ]

    URL: http://localhost:8090/company/1
    Method allowed: GET
    Response:
    {
        "id": 1,
        "name": "Company 1"
    }

    URL: http://localhost:8090/company/1/computeFile
    Method allowed: POST
    Body:
    FormData {
        "file": file.json,
        "month": 1
    }
    Response:
    {
        "id": 1,
        "month": 1,
        "score": 51,
        "company": {
            "id": 1,
            "name": "Company 1"
        }
    }
    JSON File: 
    {
        "invoices":3,
        "debits":1
    }

