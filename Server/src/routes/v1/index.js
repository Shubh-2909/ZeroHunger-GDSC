import express from "express";
import { GeminiProVisionController } from "../../controller/gemini-vision-controller.js";
import { upload } from "../../config/multer-config.js";


const router = express.Router();

router.post("/gemini-pro-vision",upload.single('image') ,GeminiProVisionController )


export default router;
