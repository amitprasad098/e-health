import { useNavigate } from "react-router-dom";
import axios from "axios";
// import "DashboardHeader.css"

interface DashboardHeaderProps {
  localStorageItem: string;
}

const DashboardHeader: React.FC<DashboardHeaderProps> = ({
  localStorageItem,
}) => {
  const navigate = useNavigate();

  const handleSignOut = async () => {
    try {
      await axios.put(`http://localhost:8080/logout`);
    } catch (error) {
      console.error("Error logging out:", error);
      // Handle error gracefully
    }
    localStorage.removeItem(localStorageItem);
    navigate("/");
  };

  return (
    <button className="btn btn-danger logout-button" onClick={handleSignOut}>
      Sign Out
    </button>
  );
};

export default DashboardHeader;
