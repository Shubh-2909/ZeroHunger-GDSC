import { genAI } from "../config/gemini-api-config.js";

async function fileToGenerativePart(buffer, mimeType) {
  return {
    inlineData: {
      data: buffer.toString("base64"),
      mimeType,
    },
  };
}

const GeminiProVision = async (textPrompt, fileBuffer) => {
  const model = genAI.getGenerativeModel({ model: "gemini-pro-vision" });
  // console.log("FileBuffer")
  // console.log(fileBuffer)
  const prompt =
    textPrompt != ""
      ? textPrompt
      : "Plant problems according to image along with the solution in pointwise format";
  const imageParts = [await fileToGenerativePart(fileBuffer, "image/jpeg")];
  // console.log("ImageParts")
  // console.log(imageParts)
  const result = await model.generateContent([prompt, ...imageParts]);

  const response = await result.response;

  const text = response.text();

  return text;
};

export { GeminiProVision };
