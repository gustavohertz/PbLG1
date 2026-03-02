import { useEffect, useState } from 'react';
import axios from 'axios';

function App() {
  const [aventureiros, setAventureiros] = useState([]);
  const [page, setPage] = useState(0);
  const [totalPages, setTotalPages] = useState(0);

  // Função para buscar dados da nossa API Spring
  const carregarAventureiros = async (pagina = 0) => {
    try {
      const res = await axios.get(`http://localhost:8080/aventureiros?page=${pagina}&size=10`);
      setAventureiros(res.data);
      // Pegando os metadados dos Headers que configuramos no Java!
      setTotalPages(parseInt(res.headers['x-total-pages']));
      setPage(pagina);
    } catch (error) {
      console.error("Erro ao buscar aventureiros da guilda", error);
    }
  };

  useEffect(() => {
    carregarAventureiros();
  }, []);

  return (
    <div style={{ padding: '20px', fontFamily: 'serif' }}>
      <h1>📜 Registro da Guilda de Aventureiros</h1>

      <table border="1" style={{ width: '100%', textAlign: 'left' }}>
        <thead>
          <tr>
            <th>ID</th>
            <th>Nome</th>
            <th>Classe</th>
            <th>Nível</th>
            <th>Status</th>
          </tr>
        </thead>
        <tbody>
          {aventureiros.map(av => (
            <tr key={av.id}>
              <td>{av.id}</td>
              <td>{av.nome}</td>
              <td>{av.classe}</td>
              <td>{av.nivel}</td>
              <td>{av.ativo ? "✅ Ativo" : "❌ Expulso"}</td>
            </tr>
          ))}
        </tbody>
      </table>

      <div style={{ marginTop: '10px' }}>
        <button disabled={page === 0} onClick={() => carregarAventureiros(page - 1)}>Anterior</button>
        <span> Página {page + 1} de {totalPages} </span>
        <button disabled={page >= totalPages - 1} onClick={() => carregarAventureiros(page + 1)}>Próxima</button>
      </div>
    </div>
  );
}

export default App;