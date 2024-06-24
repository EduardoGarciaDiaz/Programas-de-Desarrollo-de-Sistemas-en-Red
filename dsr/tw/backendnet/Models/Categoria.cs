using System.Text.Json.Serialization;

namespace backendnet.Models;

public class Categoria
{
    public int CategoriaId { get; set; }
    public required string Nombre { get; set; }
    public bool Protegida { get; set; } = false;

    // Indica que no envie al usuario esta coleccion al momento de enviar la respuesta
    [JsonIgnore]
    public ICollection<Pelicula>? Peliculas { get; set; }
}