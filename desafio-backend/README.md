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

    URL: http://localhost:8090/company/{companyId}
    Method allowed: GET
    Response:
    {
        "id": 1,
        "name": "Company 1"
    }

### Rate service

    URL: http://localhost:8090/rate/{monthValue}
    Method allowed: POST
    Body:
    FormData {
        "page": 0,
        "size": 10
    }
    Response:
    {
        "content": [
            {
                "id": 1,
                "month": 1,
                "score": 51,
                "company": {
                    "id": 1,
                    "name": "Company 1"
                }
            },
            {
                "id": 2,
                "month": 1,
                "score": 50,
                "company": {
                    "id": 2,
                    "name": "Company 2"
                }
            }
        ],
        "totalElements": 2,
        "totalPages": 1,
        "number": 0
    }
    Month options: 
    {
        {
            "name": "JANUARY",
            value: 1
        },
        ...
        {
            "name": "DECEMBER",
            value: 12
        }
    }
    
    URL: http://localhost:8090/rate/computeFile
    Method allowed: POST
    Body:
    FormData {
        "file": file.json,
        "month": 1,
        "companyId": 1
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
