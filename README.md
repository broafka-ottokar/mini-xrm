## Futtatási útmutató

- Backend:
    - `/mini-xrm-backend/development$ docker compose up`
    - `/mini-xrm-backend$ ../gradlew bootRun --args='--spring.profiles.active=dev'`
    - [http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html)
- Frontend:
    - `/mini-xrm-frontend$ npm install`
    - `/mini-xrm-frontend$ ng serve`
    - [http://localhost:4200/](http://localhost:4200/)

## Rövid architektúra leírás

- ellenőrzés arra, hogy a rétegek megfelelően épülnek fel: `/mini-xrm-backend/src/test/java/com/example/minixrm/backend/ArchUnitTest.java`
- a megszokott Persistence - Service - Controller/Web rétegek
- azzal a csavarral, hogy a Controller/Web réteg a com.example.minixrm.**core.util.facade** package-et érheti csak el, nevezzük "DtoFacade" rétegnek
    - ez a "DtoFacade" réteg com.example.minixrm.**core.facade.dto**.*Dto -kra mappeli az entitásokat, tehermentesítve a Service réteget
    - a com.example.minixrm.**core.util.mapper**.*DtoMapper-ek segítségével
- a "DtoFacade" réteget a Controller/Web réteg még egy com.example.minixrm.**web.facade**.*ViewFacade rétegen keresztül használja (nevezzük ViewFacade-nak)
    - ami Swagger generált model osztályokra mappeli a DTO-kat (ezek a com.example.minixrm.**.web.openapi.v1.model**.*View -k
    - így a Controllerekről leválasztja ennek a terhét
        - com.example.minixrm.**web.openapi.v1.model**-beli osztályokra
        - a Controllerek alapjául szolgáló interfészek is generáltak

## 3–5 technológiai döntés indoklása

1. Swagger leíróból (`/mini-xrm-backend/src/main/resources/static/openapi-v1.yaml`) történik a REST API osztályainak generálása, nem pedig Java kód alapú
    - mert ebből lehetséges az Angularnak is REST API klienst generálni, nem csúszik el a backend és a frontend
1. Postgres-ben az activity.duration_minutes lehetett volna INTERVAL típusú (ekkor elég lett volna duration-nek nevezni), de nem álltam neki kísérletezni a 3rd party library-vel
1. MapStruct-ot nem használtam, mert nem minden csapatnak felel meg, nem is vagyok benne gyakorlott
1. ilyen kis projektnél a Swagger által generált model osztályokat is lehetett volna DTO-nak használni (nem szép, de gyors és kevesebb a hibalehetőség)
     - de a technikai követelmények miatt ezt elvetettem
  
## Kompromisszumok időhiány miatt

1. az Angular frontendnél erősen támaszkodtam a Copilot agent-re
    - mert néhány hónap kihagyás miatt kimentem a gyakorlatból
    - ez eredményezett néhány furcsaságot, pl.:
        - "manuális" change detection triggerelést a paginálásnál
        - a Delete gomb long-press működése is lehetne átláthatóbb
        - a táblázat rendezésnél a mezőneveket beégette, nem a Swagger által generált enumokat használja
    - ezzel szemben a Java backend lényegében kézműves termék, ott leginkább csak Copilot kódkiegészítést használtam
1. az _tevékenység_ szerkesztő formon a partner dropdown a paginált partner listázó REST endpointból táplálkozik
  
## Furcsaságok, amikre rá kellene kérdezni éles fejesztésnél

1. a _partner_ _neve_ nem kötelező
1. a _partnernek_ van "Active" státusza és "Aktív" minősítése 
1. a _tevékenység_ _típusa_ érzésre felsorolt típus kellene legyen szabad szöveges helyett
1. a _tevékenység_ üzleti szabályai szerint a "Partner és felelős kötelező", de pl. a _tárgy_ nem, pedig logikus volna
