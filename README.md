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
Remeber to change the quantity of inserts in the for structure, because every time you decide to start the application, this class will add those values in the database. You want to avoid this, because it will have duplicated values. This project doesn't verificate this kind of duplicity at the moment.
```java
if(systemServiceRepository.findAll().size() != 4) {	
```
