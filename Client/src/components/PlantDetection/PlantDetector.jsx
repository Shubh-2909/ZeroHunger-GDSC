import { useState } from 'react';
import axios from 'axios';
import toast, {Toaster} from 'react-hot-toast';
const BACKEND_API_ENDPOINT = import.meta.env.VITE_BACKEND_API_ENDPOINT;

function PlantDetectionGenerator() {
  const [selectedFile, setSelectedFile] = useState(null);
  const [filePreview, setFilePreview] = useState('');
  const [generatedText, setGeneratedText] = useState('');
  const [textPrompt, setTextPrompt] = useState('');

  const handleFileChange = (event) => {
    const file = event.target.files[0];

    if (file) {
      
      setSelectedFile(file);

      
      const reader = new FileReader();
      reader.onloadend = () => {
        setFilePreview(reader.result);
      };
      reader.readAsDataURL(file);
    }
  };

  const handleGenerateText = async () => {
    try {
      if (!selectedFile) {
        alert('Please select a file before finding solution.');
        return;
      }

      const formData = new FormData();
      formData.append('image', selectedFile);
      formData.append('text/plain', textPrompt);


      const result = axios.post(`${BACKEND_API_ENDPOINT}`, formData)

      toast.promise(result, {
        loading: 'Generating solutions...',
        success: () => {
          return 'Solutions generated successfully!';
        },
        error: () => {
          return 'Failed to generate solution. Please try again.';
        },
      });
      
      const response = await result;
      console.log(response)

      if (response.data.success) {
        
        setGeneratedText(response.data.data || 'No solution available.');
      } else {
        alert('Failed to generate solution. Please try again.');
      }
    } catch (error) {
      console.error('Error generating solution:', error);
    }
  };

  return (
    <div className="text-center mt-8 max-w-lg mx-auto">
      <Toaster />
      <h2 className="text-4xl font-bold mb-4 max-sm:text-2xl">Upload the image of plant</h2>
      <input
        type="file"
        accept="image/*"
        onChange={handleFileChange}
        className="mb-4"
      />
      {filePreview && (
        <img src={filePreview} alt="Preview" className="max-w-full max-h-64 mb-4 rounded mx-auto" />
      )}
      <input
        type="text"
        placeholder="Enter a specific problem(if any)"
        value={textPrompt}
        onChange={(e) => setTextPrompt(e.target.value)}
        className="border border-gray-300 p-2 mb-4 w-[90%] rounded"
      />
      <br />
      <button onClick={handleGenerateText} className="bg-blue-500 text-white px-4 py-2 rounded">
        Generate Solution
      </button>
      <div className="mt-4">
        <strong>Generated Solution</strong>
        <p className="text-lg italic text-gray-700 my-2 scroll overflow-y-auto">{generatedText}</p>
      </div>
    </div>
  );
}

export default PlantDetectionGenerator;
