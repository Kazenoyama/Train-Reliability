# SNCF Train Punctuality

## Overview
This project is a fullstack web application that allows users to analyze and visualize the punctuality of SNCF trains using open data.

Users can select a specific month and explore punctuality rates, with the ability to drill down into detailed temporal evolution for each train.


## Live Demo
https://train-reliability.onrender.com/

---

## Tech Stack
- **Backend:** Java, Spring Boot (REST API)  
- **Frontend:** Angular  
- **Database:** PostgreSQL  
- **Deployment:** Render Web Service and Database (cold start free tier - 1-5 min)

---

## Features
- Display monthly train punctuality rates  
- Interactive list of trains with detailed view on demand  
- Time-based exploration via dropdown selection  
- Visual indicators for evolution (green = improvement, red = decline)  
- Deployed online (may take ~1–2 minutes on first load due to cold start)

---

## Architecture
- REST API for data processing and aggregation  
- PostgreSQL used for efficient data storage and querying  

---

## Development
This project was fully designed and developed in **1 week**, including:
- Backend development  
- Frontend implementation  
- Database setup  
- Deployment  

---

## Data Source
- SNCF open data (public datasets)
- https://ressources.data.sncf.com/explore/dataset/regularite-mensuelle-ter/information/
- https://ressources.data.sncf.com/explore/dataset/ponctualite-mensuelle-transilien/information/
- https://ressources.data.sncf.com/explore/dataset/regularite-mensuelle-tgv-aqst/information/
- https://ressources.data.sncf.com/explore/dataset/reglarite-mensuelle-tgv-nationale/information/
- https://ressources.data.sncf.com/explore/dataset/regularite-mensuelle-tgv-axes/information/
- https://ressources.data.sncf.com/explore/dataset/regularite-mensuelle-intercites/information/
