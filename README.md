Wigell Travels — REST API-mikrotjänst

Gruppuppgift i kursen Java Web Services: en portal som samlar flera av Wigellkoncernens affärstjänster bakom en gemensam gateway. Varje gruppmedlem byggde en egen mikrotjänst från grunden; detta repo är min mikrotjänst — Wigell Travels — som hanterar resor och bokningar.

Funktionalitet:

CRUD för resmål och bokningar
Separata endpoints för kund och administratör (boka, avboka, se historik, hantera resmål)
Prisberäkning i både SEK och EUR
Autentisering och auktorisering med Spring Security (Basic Auth)
Loggning till fil vid skapande, ändring och radering

Tech stack: Java 21, Spring Boot, Spring Web, Spring Security, Spring Data JPA, MySQL, Docker

Krav som uppfylldes: enhetstest med 100% täckning (service-lager), integrationstest med 100% täckning (controller → service), containeriserad med Docker i ett virtuellt nätverk tillsammans med övriga mikrotjänster och en MySQL-container.

API: nås på port 8585, t.ex. GET /api/wigelltravels/v1/travels, POST /api/wigelltravels/v1/booktrip.
