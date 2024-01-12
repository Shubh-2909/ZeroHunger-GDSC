import { useState } from "react";

const Body = () => {
  const [name, setName] = useState("Name");
  const [phone, setPhone] = useState("Phone");
  const [amount, setAmount] = useState("Amount");

  function nam(e) {
    setName(e.target.value);
  }

  function phon(e) {
    setPhone(e.target.value);
  }

  function amoun(e) {
    setAmount(e.target.value);
  }

  function handleSubmit(e) {
    e.preventDefault();
    // Add logic for handling form submission (e.g., payment processing)
  }

  return (
    <div className="container mx-auto mt-8">
      <div className="text-center">
        <p className="text-4xl font-bold mb-6">Sponsor a Meal</p>
      </div>

      <div className="flex flex-col items-center">
        <form onSubmit={handleSubmit} className="bg-gray-100 p-6 rounded-md shadow-md w-96">
          <div className="mb-4">
            <label htmlFor="name" className="text-lg font-semibold">
              Name
            </label>
            <input
              type="text"
              id="name"
              value={name}
              onChange={nam}
              onClick={() => {
                setName("");
              }}
              className="w-full p-2 border border-gray-300 rounded-md"
            />
          </div>
          <div className="mb-4">
            <label htmlFor="phone" className="text-lg font-semibold">
              Phone
            </label>
            <input
              type="text"
              id="phone"
              value={phone}
              onChange={phon}
              onClick={() => {
                setPhone("");
              }}
              className="w-full p-2 border border-gray-300 rounded-md"
            />
          </div>
          <div className="mb-4">
            <label htmlFor="amount" className="text-lg font-semibold">
              Amount
            </label>
            <input
              type="text"
              id="amount"
              value={amount}
              onChange={amoun}
              onClick={() => {
                setAmount("");
              }}
              className="w-full p-2 border border-gray-300 rounded-md"
            />
          </div>
          <button
            type="submit"
            className="w-full bg-green-500 text-white p-2 rounded-md hover:bg-green-600 focus:outline-none focus:ring focus:border-green-300"
          >
            Pay Now
          </button>
        </form>
      </div>
    </div>
  );
};

export default Body;
