import express from "express";
import bodyParser from "body-parser";
import cors from 'cors';
import dotenv from "dotenv";
dotenv.config();

import apiRoutes from "./routes/index.js";




const app = express();
const PORT = process.env.PORT || 3000;
app.use(bodyParser.json());
app.use(bodyParser.urlencoded({ extended: true }));
app.use(cors())


app.use("/api", apiRoutes);

app.get("/", (req, res) => {
    res.json({
        success: true,
        message: "Server is Live",
    });
})

const ServerAndDatabase = async () => {
    app.listen(PORT, () => {
        console.log(`Server is running on PORT ${PORT}`);
    })

   

}



ServerAndDatabase();
