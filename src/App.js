//This page imports the SplashScreen page
//and exports the app

import React, { useState } from "react";
import { ThemeProvider } from "styled-components";
import Splash from "./SplashScreen";

const LightTheme = {
  pageBackground: "white",
  titleColor: "black",
  tagLineColor: "red",
};
const DarkTheme = {
  pageBackground: "#282c36",
  titleColor: "	#ffe699",
  tagLineColor: "lavender",
  filter: "drop-shadow(0px 0px px #4444dd)",
};

const themes = {
  light: LightTheme,
  dark: DarkTheme,
};

function App() {
  const [theme, setTheme] = useState("light");

  return (
    <ThemeProvider theme={themes[theme]}>
      <Splash theme={theme} setTheme={setTheme} />
    </ThemeProvider>
  );
}

export default App;
