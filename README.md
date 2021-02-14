# Cafe POS

A simple RESTful API back-end pos application for cafe business. It provides several endpoint to do basic CRUD operation to manage data: 

* Admin
* Customer
* Payment
* Menu Order
* Menu Order Detail
* Snack

The list of API endpoint consists: 

* ` /admins`

* `/admin-types`*

* `/customers`

* `/payments`

* `/payment-methods`*

* `/menu-orders`

* `/menu-order-details`

* `/snacks`

* `/snack-types`*

* `/snack-categories`*

  \*	==>	reference entity



Available methods to manage the data are:

* GET & POST ->`/<base_endpoint>`
* GET, PUT, & DELETE -> `/<base_endpoint>/{id}`

Example: 

=> Getting customers data

`GET` `/customers`

Response: 

```
{
    "code": 200,
    "message": null,
    "data": {
        "id": 2,
        "firstName": "John",
        "lastName": "Doe",
        "email": "JohnDoe@gmail.com",
        "phone": "+12818488219"
    },
    "timestamp": "2021-02-13T14:26:03.977904225"
}
```

=> Getting 



