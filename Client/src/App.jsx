import { Route, Routes } from "react-router-dom";
import Home from "./Container/Home";

export default function App() {
  return (
    <>

    <Routes>
        <Route path="/*" element={<Home/>} />
    </Routes>
    
    

    
    </>
  )
}