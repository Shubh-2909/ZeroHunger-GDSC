## CloudCast <img src="https://img.shields.io/badge/ReactJs-white?logo=React&logoColor=blue" /> <img src="https://img.shields.io/badge/Axios-white?logo=axios&logoColor=purple" />

<details> <summary>A weather app made using React and Openweather API </summary>

- API setup with Open Weather
- API integration with Axios
- State Management using React Hooks
- Conditional Rendering of Components

</details>

<details> <summary>Repo Structure</summary>

```
.
├── public/
│   ├── icons/
│   ├── index.html
│   └── other files
├── src/
|   ├── modules/
|   |   ├── CityComponent.js
│   |   └── WeatherComponent.js
│   ├── App.js
│   ├── index.css
│   ├── SplashScreen.js
│   └── index.js
|
├── .gitignore
├── package.json
├── package-lock.json
└── README.md

```

</details>

### Deployed on

- Netlify : https://4vinn-weather.netlify.com/
- GitHub Pages : https://4vinn.github.io/React-Weather-App/

### APIs used

- [OpenWeather API](https://openweathermap.org/)
- https://openweathermap.org/current
- Method: `GET`
- URL: `https://api.openweathermap.org/data/2.5/weather?q={CITY_NAME}&appid={API_KEY}`

### Libraries used:

- styled-components
- axios
- react-scripts

### How to run:

- Clone repo
- Install node modules - `npm start`
