import { GeminiProVision } from "../services/gemini-pro-vision.js";

const GeminiProVisionController = async (req, res) => {
  try {
    const textPrompt = req.body["text/plain"];
    console.log(req.file);

    const fileBuffer = req.file.buffer;

    console.log(textPrompt);

    const result = await GeminiProVision(textPrompt, fileBuffer);

    return res.status(200).json({
      success: true,
      message: "Gemini Pro Vision API Data Fetched Successfully",
      data: result,
    });
  } catch (error) {
    console.log(error)
    return res.status(500).json({
      success: false,
      message: "Something went wrong",
      error: error.message,
    });
  }
};

export { GeminiProVisionController };
