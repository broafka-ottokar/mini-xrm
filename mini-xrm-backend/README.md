## Futtatási útmutató

  * `/mini-xrm-backend/development$ docker compose up`
  * `/mini-xrm-backend$ ../gradlew bootRun --args='--spring.profiles.active=dev'`
  * [http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html)

## Rövid architektúra leírás

  * ellenőrzés arra, hogy a rétegek megfelelően épülnek fel: /mini-xrm-backend/src/test/java/com/example/minixrm/backend/ArchUnitTest.java
  * a megszokott Persistence - Service - Controller/Web rétegek
  * azzal a csavarral, hogy a Controller/Web réteg a com.example.minixrm.**core.util.facade** package-et érheti csak el, nevezzük "DtoFacade" rétegnek
    * ez a "DtoFacade" réteg com.example.minixrm.**core.facade.dto**.*Dto -kra mappeli az entitásokat, tehermentesítve a Service réteget
    * a com.example.minixrm.**core.util.mapper**.*DtoMapper-ek segítségével
  * a "DtoFacade" réteget a Controller/Web réteg még egy com.example.minixrm.**web.facade**.*ViewFacade rétegen keresztül használja (nevezzük ViewFacade-nak)
    * ami Swagger generált model osztályokra mappeli a DTO-kat (ezek a com.example.minixrm.**.web.openapi.v1.model**.*View -k
    * így a Controllerekről leválasztja ennek a terhét
        * com.example.minixrm.**web.openapi.v1.model**-beli osztályokra
      * a Controllerek alapjául szolgáló interfészek is generáltak

## 3–5 technológiai döntés indoklása

  1. Swagger leíróból (`/mini-xrm-backend/src/main/resources/static/openapi-v1.yaml`) történik a REST API osztályainak generálása, nem pedig Java kód alapú
    * mert ebből lehetséges az Angularnak is REST API klienst generálni, nem csúszik el a kettő
  1. ilyen kis projektnél a Swagger által generált model osztályokat is lehetett volna DTO-nak használni (nem szép, de gyors és kevesebb a hibalehetőség)
  1. MapStruct által generált osztályokat érdemes lenne használni a mappeléshez, kérdés, hogy a csapatnak van-e ellenérzése (nekem is volt)
  1. Postgres-ben az activity.duration_minutes lehetett volna INTERVAL típusú (ekkor elég lett volna duration-nek nevezni), de nem álltam neki kísérletezni a 3rd party libraryval
  
## Furcsaságok, amikre rá kellene kérdezni

  1. a partner neve nem kötelező
  1. a partnernek van "Active" státusza és "Aktív" minősítése 
  1. a partner típusa érzésre felsorolt típus kellene legyen szabad szöveges helyett
  1. a tevékenység üzleti szabályainál szerint a "Partner és felelős kötelező", de pl. a tárgy nem, pedig logikus volna
  
  
  
