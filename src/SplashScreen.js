//this page imports the both CityComponent.js and WeatherComponent.js here @line 4 and 5.
//Its the main App.... and it is imported by App.js

import CityComponent from "./modules/CityComponent";
import WeatherComponent from "./modules/WeatherComponent";

import styled from "styled-components";
import { CgSun } from "react-icons/cg";
import { HiMoon } from "react-icons/hi";

import React, { useState } from "react";
import axios from "axios";

const Toggle = styled.button`
  cursor: pointer;
  height: 28px;
  width: 28px;
  border-radius: 50%;
  border: none;
  background-color: ${(props) => props.theme.titleColor};
  color: ${(props) => props.theme.pageBackground};
  &:focus {
    outline: none;
  }
  transition: all 0.5s ease;
  margin-left: 15px;
  padding-left: 4px;
`;
const Page = styled.div`
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  height: 100vh;
  width: 100vw;
  background-color: ${(props) => props.theme.pageBackground};
  transition: all 0.5s ease;
`;

const Container = styled.div`
  display: flex;
  flex-direction: column;
  margin-top: auto;
  align-items: center;
  box-shadow: 25px 25px 50px #d5d8d9, -25px -25px 50px #ffffff;
  padding: 20px 10px;
  border-radius: 35px;
  width: 380px;
  background: #f5feff;
  background: linear-gradient(-180deg, #d4dcdd, #ffffff);
  box-shadow: 24.32px 24.32px 42px #b5b8bc, -24.32px -24.32px 42px #ffffff;
  transition: all 0.5s ease;
`;

const AppTitle = styled.span`
  display: flex;
  flex-direction: column;
  color: black;
  font-size: 18px;
  font-weight: 800;
  font-family: Montserrat;
  transition: all 0.5s ease;
`;
const Top = styled.div`
  display: flex;
  flex-direction: row;
  justify-content: center;
  align-items: center;
`;
const Footer = styled.span`
  justify-content: center;
  align-items: center;
  margin: auto;
  font-weight: 600;
  color: #abaeb0;
  font-size: 14px;
  font-family: Montserrat;
  transition: all 0.5s ease;

  & a {
    text-decoration: none;
    color: #9cb7c9;
  }
  & a:hover {
    letter-spacing: 2px;
    transition: all 0.9s ease;
    text-decoration: underline;
  }
`;

function Splash(props) {
  function changeTheme() {
    if (props.theme === "light") {
      props.setTheme("dark");
    } else {
      props.setTheme("light");
    }
  }

  const [city, updateCity] = useState();
  const [weather, updateWeather] = useState();
  //defining method
  const fetchWeather = async (e) => {
    e.preventDefault();
    const response = await axios.get(
      `https://api.openweathermap.org/data/2.5/weather?q=${city}&units=metric&appid=4a4706af63ddf152d395cabd4ad11029`
    );
    updateWeather(response.data);
  };

  const icon =
    props.theme === "light" ? <HiMoon size={20} /> : <CgSun size={20} />;

  return (
    //container contains 3 parts-- title, search city page, weather page
    //weather component will showup only if weather data is present for the entered city, else it stays showing the city page
    <Page>
      <Container>
        <Top>
          <AppTitle>CloudCast</AppTitle>

          <Toggle onClick={changeTheme}>{icon}</Toggle>
        </Top>

        {weather ? (
          <WeatherComponent weather={weather}></WeatherComponent>
        ) : (
          <CityComponent
            updateCity={updateCity}
            fetchWeather={fetchWeather}
          ></CityComponent>
        )}
      </Container>
      <Footer>
        Coded by:
        <a href="https://weather.app" target="_blank" rel="noreferrer">
          {" "}
          Gaurang Agarwal{" "}
        </a>
      </Footer>
    </Page>
  );
}

export default Splash;
