import express from "express";
import v1ApiRoutes from "./v1/index.js";

const router = express.Router();

router.use('/v1', v1ApiRoutes);





export default router;