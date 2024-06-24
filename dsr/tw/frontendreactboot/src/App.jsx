import { BrowserRouter, Routes, Route } from "react-router-dom"
import Layout from "./Layout"
import Home from "./Home"
import Detalles from "./Detalles"
import NotFound from "./NotFound"

// URL de la API Nodejs con datos
const API_URL = 'http://localhost:3000/api/peliculas'

// Componente principal
const App = () => {
    return (
        <BrowserRouter>
            <Routes>
                <Route path="/" element={<Layout />}>
                    <Route index element={<Home />} />
                    <Route path="detalles/:id" element={<Detalles />} />
                    <Route path="*" element={<NotFound />} />
                </Route>
            </Routes>
        </BrowserRouter>
    );
};

export default App
export { API_URL }
