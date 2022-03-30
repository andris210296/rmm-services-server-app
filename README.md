# rmm-services-server-app
This project is Remote Monitoring and Management system that allows customers create an account, login, add device and select services. Also this project calculates the total monthly cost of the deal depending on selected services and the number of devices in database

**Important details**

Currently on **RmmServicesServerAppApplication** class, at the start of the system some Services are pre storaged due to the test requirements, as described below:
| Service Name   |      Price Windows      |  Price Mac |
|----------|:-------------:|------:|
| Antivirus |  $5 | $7 |
| Cloudberry |    $2   |   $2 |
| PSA | $2 |    $2 |
| TeamViewer | $1 |    $1 |

If you want to change or add more services, you can do it as described below:
```java

systemServiceRepository.save(new SystemService().builder()
						.id(1)
						.serviceName("Antivirus")
						.pricePerSystem(Map.of("Windows", 5, "Mac", 7))
						.build());

```
Remeber to change the quantity of inserts in the for structure, because every time you decide to start the application, this class will add those values in the database. You want to avoid this, because it will have duplicated values. At the moment this project doesn't verificate this kind of duplicity.
```java
if(systemServiceRepository.findAll().size() != 4) {	
```

# Steps to use the RMM:

##### 1. Customer creation and Authentication

**a)** In order to create a customer you must create a **POST** request at the endpoint **{server}/customer/create** with a body as decribed below:

Request: {server}/customer/create - POST
```json
{
    "userName":"user",
    "password":"password"
}
```
Response:
```json
{
    "id": 14,
    "userName": "user",
    "password": "password",
    "devices": null,
    "systemServices": null
}
```

**b)** To authenticate your customer and access the other endpoints, you must create a **POST** request at the endpoint **{server}/authenticate** and in case of success a token will be generated as result:

Request: {server}/authenticate - POST
```json
{
    "userName":"user",
    "password":"password"
}
```

Response:
```json
{
    "jwt": "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ1c3VhcmlvIiwiaWF0IjoxNjQ4NTk4NjY1LCJleHAiOjE2NDg2MzQ2NjV9.ea66L1BQMz6O0UoPxqKYYmhBP3F945FZVp-ETMk7Onw"
}
```
With this generated token, you can add to the **Header**  at **Authorization** - **Bearer {generated token}**

**c)** In case you want to verify all created customers, access this rest **{server}/customer/getAllCustomers** with a **GET** request. Also add the generated token at the **Header**:

Request: {server}/customer/getAllCustomers - GET
Header: Authorization - Bearer {token}

Response:
```json
[
    {
        "id": 5,
        "userName": "user",
        "password": "password",
        "devices": [],
        "systemServices": []
    },
    {
        "id": 14,
        "userName": "newUser",
        "password": "otherPassword",
        "devices": [],
        "systemServices": []
    }
]
```

##### 2. Handling Devices

**a)** To create a device for a customer, send a **POST**  request **authenticated** to this URL **{server}/device/create** with a body as described below:

Request: {server}/device/create - POST
Header: Authorization - Bearer {token}
```json
{
    "systemName":"Windows PC",
    "type":"Windows"
}
```

Response:
```json
{
    "userName": "user",
    "devices": [
        {
            "id": 13,
            "systemName": "Windows PC",
            "type": "Windows"
        }
    ]
}
```
**Important:** In order to work properly write correctly the words **Windows**, **Windows Workstation**, **Windows Server** or **Mac**, further updates will allow different kinds of Operating Systems. Those OPs were defined by the task documentation.

**b)** In case you want to verify all created devices, access this rest **{server}/device/myDevices** with a **GET** request. Also add the generated token at the **Header**:

Request: {server}/device/myDevices - GET
Header: Authorization - Bearer {token}

Response:
```json
{
    "userName": "user",
    "devices": [
        {
            "id": 13,
            "systemName": "Windows PC",
            "type": "Windows"
        },
        {
            "id": 15,
            "systemName": "Mac computer",
            "type": "Mac"
        }
    ]
}
```

**c)** To recover a specific device make a **GET** request to this API **{server}/device/myDevices/{id}** and change the **{id}** for a valid device **id**, remender to add the **authentication**

Request: {server}/device/myDevices/{id} - GET
Header: Authorization - Bearer {token}

Response:
```json
{
    "userName": "user",
    "devices": [
        {
            "id": 13,
            "systemName": "Windows PC",
            "type": "Windows"
        }
    ]
}
```

**d)** To update a device send a **PUT**  request **authenticated** to this URL **{server}/device/myDevices/{id}** with the device id that you want to change and a **body** with new values to be changed:

Request: {server}/device/myDevices/{id} - PUT
Header: Authorization - Bearer {token}
```json
{
    "systemName":"New System",
    "type":"Mac"
}
```

Response:
```json
{
    "userName": "user",
    "devices": [
        {
            "id": 13,
            "systemName": "New System",
            "type": "Mac"
        }
    ]
}
```

**e)** To delete a device send a **DELETE**  request **authenticated** to this URL **{server}/device/myDevices/{id}** with the device id that you want to delete:

Request: {server}/device/myDevices/{id} - DELETE
Header: Authorization - Bearer {token}

Response: Status 200 - OK

##### 3. Handling Services
