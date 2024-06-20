import { useState } from "react";
import axios from "axios";
import "./FindPharmaciesTab.css";

const FindPharmaciesTab = () => {
  const [city, setCity] = useState("");
  const [pharmacies, setPharmacies] = useState<any[]>([]);

  const handleSearch = async () => {
    try {
      const response = await axios.get(
        `http://localhost:8080/pharmacies/${city}`
      );
      setPharmacies(response.data);
    } catch (error) {
      console.error("Error fetching pharmacies:", error);
    }
  };

  return (
    <div className="p-3">
      <h2 id="pharmacy-heading">Find Pharmacies</h2>
      <div className="pharmacy-search-container">
        <input
          type="text"
          value={city}
          onChange={(e) => setCity(e.target.value)}
          placeholder="Enter city name"
          className="m-3"
        />
        <button onClick={handleSearch} className="btn btn-primary m-3">
          Search
        </button>
      </div>
      <div className="pharmacy-table p-2">
        <h3>Pharmacies:</h3>
        <ul className="pharmacy-list-container">
          {pharmacies.map((pharmacy) => (
            <li key={pharmacy.pharmacyId}>
              <h4>{pharmacy.pharmacyName}</h4>
              <div className="row m-1">
                <p className="col-sm-12 col-md-6">
                  Address: {pharmacy.pharmacyAddress}
                </p>
                <p className="col-sm-12 col-md-6">
                  Contact Number: {pharmacy.pharmacyContactNumber}
                </p>
              </div>
              <div className="row m-1">
                <p className="col-sm-12 col-md-6">
                  Opening Times: {pharmacy.pharmacyOpeningTimes}
                </p>

                {/* Display a shortened version of the link text */}
                <p className="col-sm-12 col-md-6">
                  Map Direction:{" "}
                  <a
                    href={pharmacy.pharmacyMapUrl}
                    target="_blank"
                    rel="noopener noreferrer"
                  >
                    View Map
                  </a>{" "}
                </p>
              </div>
            </li>
          ))}
        </ul>
      </div>
    </div>
  );
};

export default FindPharmaciesTab;
