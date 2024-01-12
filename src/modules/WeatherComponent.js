////this page is imported by SplashScreen.js
//page2 of container box
//this contains the parts that are shown on Container box, that opens up after searching the city

import styled from "styled-components";
import SearchAgain from '../SearchAgain.png'
import sunset from '../icon/sunset.png'
import sunrise from '../icon/sunrise.png'
import humidity from '../icon/humidity.png'
import wind from '../icon/wind.png'
import clouds from '../icon/clouds.png'
import sunny from '../icon/sunny2.png'
import night from '../icon/night.png'
import day from '../icon/day.png'
import cloudyNight from '../icon/cloudy-night.png'
import scattered2 from '../icon/scattered2.png'
import broken3 from '../icon/broken3.png'
import broken4 from '../icon/broken4.png'
import shower from '../icon/shower.png'
import rainyday from '../icon/rain-day.png'
import rainynight from '../icon/rain-night.png'
import storm from '../icon/storm.png'
import snow3 from '../icon/snow3.png'
import snow from '../icon/snow.png'
import mist2 from '../icon/mist2.png'
import mist3 from '../icon/mist3.png'


const WeatherCondition = styled.div`
  display: flex;
  flex-direction: row;
  align-items: center;
  width: 100%;
  justify-content: space-between;
  margin: 30px auto;
`;
const Condition = styled.span`
  margin: 20px auto;
  font-size: 16px;
  font-family: Exo;
  font-weight: 400;

  text-transform: capitalize;
  & span {
    font-size: 28px;
  }
`;
const WeatherLogo = styled.img`
  width: 100px;
  height: 100px;
  margin: 5px auto;
`;
const Location = styled.span`
  font-size: 28px;
  font-weight: bold;
`;
const NewSearch = styled.span`
  padding-left: 10px;
  padding-top: 5px;
  height: 22px;
  width: 22px;
  cursor: pointer;
  background-color: none;
  & img {
    width: 100%;
    height: 100%;
  }
`;
//contains both location and the magnify image(newsearch)
const InfoLocation = styled.span`
  display: flex;
  flex-direction: row;
  height: 40px;
  justify-content: center;
  align-items: center;
`;

const WeatherInfolabel = styled.span`
  font-size: 14px;
  font-weight: bold;
  margin: 20px 25px 10px;
  text-align: start;
  width: 90%;
`;
const WeatherInfoContainer = styled.div`
  display: flex;
  width: 90%;
  flex-direction: row;
  justify-content: space-evenly;
  align-items: center;
  flex-wrap: wrap;
`;
const InfoContainer = styled.div`
  display: flex;
  margin: 5px 10px;
  flex-direction: row;
  justify-content: space-evenly;
  align-items: center;
`;
const InfoIcon = styled.img`
  width: 36px;
  height: 36px;
`;
const InfoLabel = styled.span`
  display: flex;
  flex-direction: column;
  font-size: 14px;
  margin: 15px;
  & span {
    font-size: 12px;
    text-transform: capitalize;
  }
`;

//uses id given to weather info components
const WeatherInfoicon = {
  sunrise: sunrise,
  sunset: sunset,
  humidity: humidity,
  wind: wind,
  clouds: clouds,
  visibility: clouds,
};
export const Weathericon = {
  "01d": sunny,
  "01n": night,
  "02d": day,
  "02n": cloudyNight,
  "03d": scattered2,
  "03n": scattered2,
  "04d": broken4,
  "04n": broken3,
  "09d": shower,
  "09n": shower,
  "10d": rainyday,
  "10n": rainynight,
  "11d": storm,
  "11n": storm,
  "13d": snow3,
  "13n": snow,
  "50d": mist2,
  "50n": mist3,
};

function convertUnixTimestampToTime(unixTimestamp) {
  // Multiply by 1000 to convert from seconds to milliseconds
  const date = new Date(unixTimestamp * 1000);

  // Get hours, minutes, and seconds
  const hours = date.getHours();
  const minutes = date.getMinutes();
  const seconds = date.getSeconds();

  // Format the time as HH:MM:SS
  const formattedTime = `${String(hours).padStart(2, "0")}:${String(
    minutes
  ).padStart(2, "0")}:${String(seconds).padStart(2, "0")}`;

  return formattedTime;
}

const WeatherInfoComponent = (props) => {
  const { name, value, id } = props;

  return (
    <InfoContainer>{console.log(id)}
      <InfoIcon src={WeatherInfoicon[`${id}`]}></InfoIcon>
      <InfoLabel>
        {value}
        <span>{name}</span>
      </InfoLabel>
    </InfoContainer>
  );
};

const WeatherComponent = (props) => {
  const { weather } = props;

  return (
    <>
      <WeatherCondition>
        <Condition>
          <span>{`${weather?.main?.temp}°C`} | </span>
          <div>{weather?.weather[0].description}</div>{" "}
          <div>{`Feels like ${weather?.main?.feels_like}°C`}</div>{" "}
        </Condition>
        <WeatherLogo src={Weathericon[weather?.weather[0].icon]}></WeatherLogo>
      </WeatherCondition>

      <InfoLocation>
        <Location>{` ${weather?.name},${weather?.sys.country} `} </Location>
        <NewSearch>
            <img src={SearchAgain} alt="SearchAgain" />
          <a href="http://localhost:3000/React-Weather-App">
          </a>
        </NewSearch>
      </InfoLocation>

      <WeatherInfolabel>Weather Info</WeatherInfolabel>

      <WeatherInfoContainer>
        <WeatherInfoComponent
          id="sunrise"
          name="sunrise"
          value={`${convertUnixTimestampToTime(weather?.sys?.sunrise)}`}
        ></WeatherInfoComponent>

        <WeatherInfoComponent
          id="sunset"
          name="sunset"
          value={`${convertUnixTimestampToTime(weather?.sys?.sunset)}`}
        ></WeatherInfoComponent>

        <WeatherInfoComponent
          id="humidity"
          name="humidity"
          value={`${weather?.main?.humidity}%`}
        ></WeatherInfoComponent>

        <WeatherInfoComponent
          id="wind"
          name="wind"
          value={`${weather?.wind?.speed}m/s`}
        ></WeatherInfoComponent>

        <WeatherInfoComponent
          id="visibility"
          name="visibility"
          value={`${weather?.visibility} m`}
        ></WeatherInfoComponent>

        <WeatherInfoComponent
          id="clouds"
          name="Cloud Cover"
          value={`${weather?.clouds?.all}%`}
        ></WeatherInfoComponent>
      </WeatherInfoContainer>
    </>
  );
};
export default WeatherComponent;
