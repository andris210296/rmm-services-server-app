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

a. In order to create a customer you must create a **POST** request at the endpoint **localhost:8080/customer/create** with a body as decribed below:

Request: localhost:8080/customer/create
```json
{
    "userName":"login",
    "password":"anypassowrd"
}
```
b. To authenticate your customer and access the other endpoints, you must create a **POST** request at the endpoint **localhost:8080/authenticate** and in case of success a token will be generated as result:

Request: localhost:8080/authenticate 
```json
{
    "userName":"login",
    "password":"anypassowrd"
}
```

Response:
```json
{
    "jwt": "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ1c3VhcmlvIiwiaWF0IjoxNjQ4NTk4NjY1LCJleHAiOjE2NDg2MzQ2NjV9.ea66L1BQMz6O0UoPxqKYYmhBP3F945FZVp-ETMk7Onw"
}
```
With this generated token, you can add to the **Header**  at **Authorization** - **Bearer {generated token}**

c. In case you want to verify all created customers, access this rest **localhost:8080/customer/getAllCustomers** with a **GET** request. Also add the generated token at the **Header**:

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

