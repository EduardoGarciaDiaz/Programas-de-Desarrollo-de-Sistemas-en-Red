using mvc;
using MySqlConnector;

/// Registrar servicios que la app usará y construirla

var builder = WebApplication.CreateBuilder(args);

// Agrega el soporte para MySQL
builder.Services.AddMySqlDataSource(builder.Configuration.GetConnectionString("DataContext")!);

// Agrega la funcionalidad de MVC
builder.Services.AddControllersWithViews();

// Soporte para consultar los datos
builder.Services.AddScoped<IDataContext, DataContext>();

// Construye la app web
var app = builder.Build();

/// Configuracion y funcionalidad de servicios
// Configura la HTTP request pipeline
if (!app.Environment.IsDevelopment())
{
    // En caso de error en producción, oculta los errores y manda una pagina personalizada
    app.UseExceptionHandler("/Home/Error");
    // Establece que la app debe ejecutarse en HTTPS
    app.UseHsts();
}

// Utiliza rutas para los endpoints de los controladores
app.UseRouting();

// Establece el patrón de rutas
app.MapControllerRoute(
    name: "default",
    pattern: "{controller=Home}/{action=Index}/{id?}");

app.Run();