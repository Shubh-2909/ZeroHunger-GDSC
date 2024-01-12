import { Route, Routes } from "react-router-dom";
import PlantDetector from "../components/PlantDetection/PlantDetector";
import Navbar from "../components/Navbar/Navbar";
import Homepage from "../components/Homepage/Homepage";
import Footer from "../components/Footer/Footer";

function Home() {
  return (
    <>
      <Navbar />

      <Routes>
        <Route path="/" element={<Homepage />} />
        <Route
          path="/plant-disease-detection"
          element={<PlantDetector />}
        />
      </Routes>
      <Footer/>
    </>
  );
}
export default Home;
