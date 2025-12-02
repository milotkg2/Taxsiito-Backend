package com.taxsiito.backend.config;

import com.taxsiito.backend.model.*;
import com.taxsiito.backend.model.enums.EstadoOrden;
import com.taxsiito.backend.model.enums.Rol;
import com.taxsiito.backend.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.LinkedHashMap;

/**
 * Seeder que carga datos iniciales en la base de datos.
 * Se ejecuta automaticamente al iniciar la aplicacion.
 * Contrasenas en texto plano (sin encriptacion).
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class DataSeeder implements CommandLineRunner {

    private final UsuarioRepository usuarioRepository;
    private final CategoriaRepository categoriaRepository;
    private final ProductoRepository productoRepository;
    private final PreguntaFrecuenteRepository preguntaRepository;
    private final RegionRepository regionRepository;
    private final ComunaRepository comunaRepository;
    private final OrdenRepository ordenRepository;


    @Override
    public void run(String... args) {
        log.info("Verificando datos iniciales...");
        
        seedRegionesYComunas();
        seedUsuarios();
        seedCategorias();
        seedProductos();
        seedPreguntasFrecuentes();
        seedOrdenes();
        
        log.info("Datos iniciales verificados correctamente");
    }

    /**
     * Crea regiones y comunas de Chile si no existen.
     */
    private void seedRegionesYComunas() {
        if (regionRepository.count() == 0) {
            log.info("Creando regiones y comunas de Chile...");

            // Mapa de regiones con sus comunas
            Map<String, List<String>> regionesComunas = new LinkedHashMap<>();

            regionesComunas.put("Arica y Parinacota", Arrays.asList(
                "Arica", "Camarones", "General Lagos", "Putre"
            ));

            regionesComunas.put("Tarapaca", Arrays.asList(
                "Alto Hospicio", "Camina", "Colchane", "Huara", "Iquique", "Pica", "Pozo Almonte"
            ));

            regionesComunas.put("Antofagasta", Arrays.asList(
                "Antofagasta", "Calama", "Maria Elena", "Mejillones", "Olague", 
                "San Pedro de Atacama", "Sierra Gorda", "Taltal", "Tocopilla"
            ));

            regionesComunas.put("Atacama", Arrays.asList(
                "Alto del Carmen", "Caldera", "Chanaral", "Copiapo", "Diego de Almagro", 
                "Freirina", "Huasco", "Tierra Amarilla", "Vallenar"
            ));

            regionesComunas.put("Coquimbo", Arrays.asList(
                "Andacollo", "Canela", "Combarbala", "Coquimbo", "Illapel", "La Higuera", 
                "La Serena", "Los Vilos", "Monte Patria", "Ovalle", "Paihuano", 
                "Punitaqui", "Rio Hurtado", "Salamanca", "Vicuna"
            ));

            regionesComunas.put("Valparaiso", Arrays.asList(
                "Algarrobo", "Cabildo", "Calera", "Calle Larga", "Cartagena", "Casablanca", 
                "Catemu", "Concon", "El Quisco", "El Tabo", "Hijuelas", "Isla de Pascua", 
                "Juan Fernandez", "La Cruz", "La Ligua", "Limache", "Llaillay", "Los Andes", 
                "Nogales", "Olmue", "Panquehue", "Papudo", "Petorca", "Puchuncavi", "Putaendo", 
                "Quillota", "Quilpue", "Quintero", "Rinconada", "San Antonio", "San Esteban", 
                "San Felipe", "Santa Maria", "Santo Domingo", "Valparaiso", "Villa Alemana", 
                "Vina del Mar", "Zapallar"
            ));

            regionesComunas.put("Metropolitana", Arrays.asList(
                "Alhue", "Buin", "Calera de Tango", "Cerrillos", "Cerro Navia", "Colina", 
                "Conchali", "Curacavi", "El Bosque", "El Monte", "Estacion Central", 
                "Huechuraba", "Independencia", "Isla de Maipo", "La Cisterna", "La Florida", 
                "La Granja", "La Pintana", "La Reina", "Lampa", "Las Condes", "Lo Barnechea", 
                "Lo Espejo", "Lo Prado", "Macul", "Maipu", "Maria Pinto", "Melipilla", "Nunoa", 
                "Padre Hurtado", "Paine", "Pedro Aguirre Cerda", "Penalolen", "Penaflor", 
                "Pirque", "Providencia", "Pudahuel", "Puente Alto", "Quilicura", "Quinta Normal", 
                "Recoleta", "Renca", "San Bernardo", "San Joaquin", "San Jose de Maipo", 
                "San Miguel", "San Pedro", "San Ramon", "Santiago", "Talagante", "Tiltil", "Vitacura"
            ));

            regionesComunas.put("O'Higgins", Arrays.asList(
                "Chimbarongo", "Chepica", "Codegua", "Coinco", "Coltauco", "Donihue", "Graneros", 
                "La Estrella", "Las Cabras", "Litueche", "Lolol", "Machali", "Malloa", "Marchigue", 
                "Mostazal", "Nancagua", "Navidad", "Olivar", "Palmilla", "Paredones", "Peralillo", 
                "Peumo", "Pichidegua", "Pichilemu", "Placilla", "Pumanque", "Quinta de Tilcoco", 
                "Rancagua", "Rengo", "Requinoa", "San Fernando", "San Vicente", "Santa Cruz"
            ));

            regionesComunas.put("Maule", Arrays.asList(
                "Cauquenes", "Chanco", "Colbun", "Constitucion", "Curepto", "Curico", "Empedrado", 
                "Hualane", "Licanten", "Linares", "Longavi", "Maule", "Molina", "Parral", "Pelarco", 
                "Pelluhue", "Pencahue", "Rauco", "Retiro", "Rio Claro", "Romeral", "Sagrada Familia", 
                "San Clemente", "San Javier", "San Rafael", "Talca", "Teno", "Vichuquen", 
                "Villa Alegre", "Yerbas Buenas"
            ));

            regionesComunas.put("Nuble", Arrays.asList(
                "Bulnes", "Chillan", "Chillan Viejo", "Cobquecura", "Coelemu", "Coihueco", 
                "El Carmen", "Ninhue", "Niquen", "Pemuco", "Pinto", "Portezuelo", "Quillon", 
                "Quirihue", "Ranquil", "San Carlos", "San Fabian", "San Ignacio", "San Nicolas", 
                "Treguaco", "Yungay"
            ));

            regionesComunas.put("Biobio", Arrays.asList(
                "Alto Biobio", "Antuco", "Arauco", "Cabrero", "Canete", "Chiguayante", "Concepcion", 
                "Contulmo", "Coronel", "Curanilahue", "Florida", "Hualpen", "Hualqui", "Laja", 
                "Lebu", "Los Alamos", "Los Angeles", "Lota", "Mulchen", "Nacimiento", "Negrete", 
                "Penco", "Quilaco", "Quilleco", "San Pedro de la Paz", "San Rosendo", 
                "Santa Barbara", "Santa Juana", "Talcahuano", "Tirua", "Tome", "Tucapel", "Yumbel"
            ));

            regionesComunas.put("Araucania", Arrays.asList(
                "Angol", "Carahue", "Cholchol", "Collipulli", "Cunco", "Curacautin", "Curarrehue", 
                "Ercilla", "Freire", "Galvarino", "Gorbea", "Lautaro", "Loncoche", "Lonquimay", 
                "Los Sauces", "Lumaco", "Melipeuco", "Nueva Imperial", "Padre Las Casas", 
                "Perquenco", "Pitrufquen", "Pucon", "Puren", "Renaico", "Saavedra", "Temuco", 
                "Teodoro Schmidt", "Tolten", "Traiguen", "Victoria", "Vilcun", "Villarrica"
            ));

            regionesComunas.put("Los Rios", Arrays.asList(
                "Corral", "Futrono", "La Union", "Lago Ranco", "Lanco", "Los Lagos", "Mafil", 
                "Mariquina", "Paillaco", "Panguipulli", "Rio Bueno", "Valdivia"
            ));

            regionesComunas.put("Los Lagos", Arrays.asList(
                "Ancud", "Calbuco", "Castro", "Chaiten", "Chonchi", "Cochamo", "Curaco de Velez", 
                "Dalcahue", "Fresia", "Frutillar", "Futaleufu", "Hualaihue", "Llanquihue", 
                "Los Muermos", "Maullin", "Osorno", "Palena", "Puerto Montt", "Puerto Octay", 
                "Puerto Varas", "Puqueldon", "Purranque", "Puyehue", "Queilen", "Quellon", 
                "Quemchi", "Quinchao", "Rio Negro", "San Juan de la Costa", "San Pablo"
            ));

            regionesComunas.put("Aysen", Arrays.asList(
                "Aysen", "Chile Chico", "Cisnes", "Cochrane", "Coyhaique", "Guaitecas", 
                "Lago Verde", "O'Higgins", "Rio Ibanez", "Tortel"
            ));

            regionesComunas.put("Magallanes", Arrays.asList(
                "Antartica", "Cabo de Hornos", "Laguna Blanca", "Natales", "Porvenir", 
                "Primavera", "Punta Arenas", "Rio Verde", "San Gregorio", "Timaukel", 
                "Torres del Paine"
            ));

            int totalComunas = 0;
            int regionCount = 0;

            for (Map.Entry<String, List<String>> entry : regionesComunas.entrySet()) {
                regionCount++;
                String codigoRegion = String.format("%02d", regionCount);
                
                Region region = Region.builder()
                        .nombre(entry.getKey())
                        .codigo(codigoRegion)
                        .build();
                
                Region regionGuardada = regionRepository.save(region);

                for (String nombreComuna : entry.getValue()) {
                    Comuna comuna = Comuna.builder()
                            .nombre(nombreComuna)
                            .region(regionGuardada)
                            .build();
                    comunaRepository.save(comuna);
                    totalComunas++;
                }
            }

            log.info("- {} regiones creadas", regionCount);
            log.info("- {} comunas creadas", totalComunas);
        }
    }

    /**
     * Crea usuarios de prueba si no existen.
     */
    private void seedUsuarios() {
        if (usuarioRepository.count() == 0) {
            log.info("Creando usuarios de prueba...");

            // Obtener Region Metropolitana y sus comunas
            Region metropolitana = regionRepository.findByNombre("Metropolitana").orElse(null);
            Comuna santiago = metropolitana != null ? 
                comunaRepository.findByNombreAndRegion("Santiago", metropolitana).orElse(null) : null;
            Comuna lasCondes = metropolitana != null ? 
                comunaRepository.findByNombreAndRegion("Las Condes", metropolitana).orElse(null) : null;
            Comuna nunoa = metropolitana != null ? 
                comunaRepository.findByNombreAndRegion("Nunoa", metropolitana).orElse(null) : null;

            List<Usuario> usuarios = Arrays.asList(
                    Usuario.builder()
                            .run("111111111")
                            .nombres("Admin")
                            .apellidos("TaxSIIto")
                            .correo("admin@gmail.com")
                            .password("admin1234")
                            .rol(Rol.ADMIN)
                            .direccion("Av Providencia 1234")
                            .region(metropolitana)
                            .comuna(santiago)
                            .telefono("+56912345678")
                            .build(),

                    Usuario.builder()
                            .run("222222222")
                            .nombres("Vendedor")
                            .apellidos("Demo")
                            .correo("vendedor@gmail.com")
                            .password("vendedor1234")
                            .rol(Rol.VENDEDOR)
                            .direccion("Av Las Condes 5678")
                            .region(metropolitana)
                            .comuna(lasCondes)
                            .telefono("+56987654321")
                            .build(),

                    Usuario.builder()
                            .run("333333333")
                            .nombres("Cliente")
                            .apellidos("Prueba")
                            .correo("cliente@gmail.com")
                            .password("cliente1234")
                            .rol(Rol.CLIENTE)
                            .direccion("Av Nunoa 910")
                            .region(metropolitana)
                            .comuna(nunoa)
                            .telefono("+56911223344")
                            .build()
            );

            usuarioRepository.saveAll(usuarios);
            log.info("- {} usuarios creados", usuarios.size());
        }
    }

    /**
     * Crea categorias de productos si no existen.
     */
    private void seedCategorias() {
        if (categoriaRepository.count() == 0) {
            log.info("Creando categorias...");

            List<Categoria> categorias = Arrays.asList(
                    Categoria.builder()
                            .nombre("Accesorios")
                            .descripcion("Lanyards, llaveros, mousepads y mas")
                            .build(),
                    Categoria.builder()
                            .nombre("Coleccion")
                            .descripcion("Figuras y articulos de coleccion")
                            .build(),
                    Categoria.builder()
                            .nombre("Indumentaria")
                            .descripcion("Ropa y prendas oficiales")
                            .build(),
                    Categoria.builder()
                            .nombre("Hogar")
                            .descripcion("Articulos para el hogar")
                            .build(),
                    Categoria.builder()
                            .nombre("Regalos")
                            .descripcion("Cajas sorpresa y regalos especiales")
                            .build()
            );

            categoriaRepository.saveAll(categorias);
            log.info("- {} categorias creadas", categorias.size());
        }
    }

    /**
     * Crea productos de prueba si no existen.
     */
    private void seedProductos() {
        if (productoRepository.count() == 0) {
            log.info("Creando productos...");

            Categoria accesorios = categoriaRepository.findByNombre("Accesorios").orElse(null);
            Categoria coleccion = categoriaRepository.findByNombre("Coleccion").orElse(null);
            Categoria indumentaria = categoriaRepository.findByNombre("Indumentaria").orElse(null);
            Categoria hogar = categoriaRepository.findByNombre("Hogar").orElse(null);
            Categoria regalos = categoriaRepository.findByNombre("Regalos").orElse(null);

            List<Producto> productos = Arrays.asList(
                    Producto.builder()
                            .codigo("P-0001")
                            .nombre("Lanyard con porta tarjeta")
                            .descripcion("Lanyard con porta tarjeta. Practico y resistente.")
                            .precio(BigDecimal.valueOf(1800))
                            .stock(50)
                            .stockCritico(5)
                            .imagen("img/langer.png")
                            .categoria(accesorios)
                            .build(),

                    Producto.builder()
                            .codigo("P-0002")
                            .nombre("Juguete ChatSIIto")
                            .descripcion("Mini figura coleccionable del ChatSIIto.")
                            .precio(BigDecimal.valueOf(5990))
                            .stock(30)
                            .stockCritico(5)
                            .imagen("img/juguete.png")
                            .categoria(coleccion)
                            .build(),

                    Producto.builder()
                            .codigo("P-0003")
                            .nombre("Llavero TaxSIIto")
                            .descripcion("Llavero oficial para tus llaves.")
                            .precio(BigDecimal.valueOf(3990))
                            .stock(80)
                            .stockCritico(5)
                            .imagen("img/llavero.png")
                            .categoria(accesorios)
                            .build(),

                    Producto.builder()
                            .codigo("P-0004")
                            .nombre("Mousepad TaxSIIto")
                            .descripcion("Mousepad comodo y antideslizante.")
                            .precio(BigDecimal.valueOf(5900))
                            .stock(25)
                            .stockCritico(5)
                            .imagen("img/mousepad.png")
                            .categoria(accesorios)
                            .build(),

                    Producto.builder()
                            .codigo("P-0005")
                            .nombre("Labubu ChatSIIto")
                            .descripcion("Figura edicion limitada del ChatSIIto.")
                            .precio(BigDecimal.valueOf(24990))
                            .stock(10)
                            .stockCritico(3)
                            .imagen("img/labubu.png")
                            .categoria(coleccion)
                            .build(),

                    Producto.builder()
                            .codigo("P-0006")
                            .nombre("Caja sorpresa")
                            .descripcion("Una sorpresa tematica TaxSIIto.")
                            .precio(BigDecimal.valueOf(9990))
                            .stock(40)
                            .stockCritico(5)
                            .imagen("img/caja sorpresa.png")
                            .categoria(regalos)
                            .build(),

                    Producto.builder()
                            .codigo("P-0007")
                            .nombre("Poleron TaxSIIto Oficial")
                            .descripcion("Poleron comodo y abrigado.")
                            .precio(BigDecimal.valueOf(19990))
                            .stock(15)
                            .stockCritico(5)
                            .imagen("img/poleron.png")
                            .categoria(indumentaria)
                            .build(),

                    Producto.builder()
                            .codigo("P-0008")
                            .nombre("Taza ChatSIIto")
                            .descripcion("Taza para tus mananas")
                            .precio(BigDecimal.valueOf(6990))
                            .stock(60)
                            .stockCritico(5)
                            .imagen("img/tasa.png")
                            .categoria(hogar)
                            .build()
            );

            productoRepository.saveAll(productos);
            log.info("- {} productos creados", productos.size());
        }
    }

    /**
     * Crea preguntas frecuentes para el ChatSIIto si no existen.
     */
    private void seedPreguntasFrecuentes() {
        if (preguntaRepository.count() == 0) {
            log.info("Creando preguntas frecuentes...");

            List<PreguntaFrecuente> faqs = Arrays.asList(
                    PreguntaFrecuente.builder()
                            .pregunta("Que es el IVA y cuando debo pagarlo?")
                            .respuesta("El IVA (Impuesto al Valor Agregado) es un impuesto del 19% que se aplica a la venta de bienes y servicios. Si eres contribuyente de IVA, debes declararlo y pagarlo mensualmente a traves del Formulario 29.")
                            .categoriaPregunta("Impuestos")
                            .ordenVisualizacion(1)
                            .build(),

                    PreguntaFrecuente.builder()
                            .pregunta("Como emito una boleta electronica?")
                            .respuesta("Para emitir una boleta electronica debes: 1) Ingresar al portal del SII con tu RUT y clave. 2) Ir a 'Servicios online' > 'Boleta electronica'. 3) Completar los datos del documento. 4) Enviar al correo del cliente.")
                            .categoriaPregunta("Documentos")
                            .ordenVisualizacion(2)
                            .build(),

                    PreguntaFrecuente.builder()
                            .pregunta("Cuando vence la declaracion mensual de IVA?")
                            .respuesta("La declaracion mensual de IVA (F29) vence el dia 12 del mes siguiente al periodo tributario. Si el dia 12 cae en fin de semana o feriado, el vencimiento se extiende al dia habil siguiente.")
                            .categoriaPregunta("Plazos")
                            .ordenVisualizacion(3)
                            .build(),

                    PreguntaFrecuente.builder()
                            .pregunta("Que es el PPM y como se calcula?")
                            .respuesta("El PPM (Pago Provisional Mensual) es un anticipo del Impuesto a la Renta. Se calcula aplicando un porcentaje a tus ingresos brutos mensuales. La tasa varia segun tu situacion tributaria.")
                            .categoriaPregunta("Impuestos")
                            .ordenVisualizacion(4)
                            .build(),

                    PreguntaFrecuente.builder()
                            .pregunta("Como inicio actividades como emprendedor?")
                            .respuesta("Para iniciar actividades: 1) Ingresa a sii.cl. 2) Selecciona 'Inicio de actividades'. 3) Completa el formulario con tus datos y actividad economica. 4) Elige tu regimen tributario (Pro Pyme, Renta Presunta, etc.).")
                            .categoriaPregunta("Inicio actividades")
                            .ordenVisualizacion(5)
                            .build(),

                    PreguntaFrecuente.builder()
                            .pregunta("Que gastos puedo deducir como empresa?")
                            .respuesta("Puedes deducir gastos necesarios para producir renta: arriendo de oficina, sueldos, servicios basicos, materiales, combustible, entre otros. Deben estar respaldados con facturas y ser proporcionales a tu giro.")
                            .categoriaPregunta("Impuestos")
                            .ordenVisualizacion(6)
                            .build(),

                    PreguntaFrecuente.builder()
                            .pregunta("Como obtengo mi clave tributaria del SII?")
                            .respuesta("Puedes obtener tu clave: 1) En linea a traves de sii.cl si tienes cedula de identidad vigente. 2) Presencialmente en oficinas del SII. 3) A traves de ClaveUnica del Estado.")
                            .categoriaPregunta("Acceso SII")
                            .ordenVisualizacion(7)
                            .build(),

                    PreguntaFrecuente.builder()
                            .pregunta("Que es una factura electronica y cuando usarla?")
                            .respuesta("La factura electronica es un documento tributario digital que respalda la venta de bienes o servicios entre empresas. Debes usarla cuando vendes a otras empresas que necesitan acreditar IVA como credito fiscal.")
                            .categoriaPregunta("Documentos")
                            .ordenVisualizacion(8)
                            .build()
            );

            preguntaRepository.saveAll(faqs);
            log.info("- {} preguntas frecuentes creadas", faqs.size());
        }
    }

    /**
     * Crea 4 ordenes de prueba si no existen.
     */
    private void seedOrdenes() {
        long count = ordenRepository.count();
        log.info("Verificando ordenes existentes: {}", count);
        
        if (count == 0) {
            log.info("Creando ordenes de prueba...");

            // Obtener usuarios y productos para las ordenes
            Usuario cliente = usuarioRepository.findByCorreo("cliente@gmail.com").orElse(null);
            Usuario admin = usuarioRepository.findByCorreo("admin@gmail.com").orElse(null);
            List<Producto> productos = productoRepository.findAll();

            log.info("Usuarios encontrados - Cliente: {}, Admin: {}, Productos: {}", 
                cliente != null ? cliente.getCorreo() : "NO ENCONTRADO",
                admin != null ? admin.getCorreo() : "NO ENCONTRADO",
                productos.size());

            if (cliente == null || admin == null || productos.isEmpty()) {
                log.warn("No se pueden crear ordenes: faltan usuarios o productos");
                return;
            }

            // Orden 1: Pendiente
            Orden orden1 = Orden.builder()
                    .numeroOrden("ORD-001")
                    .usuario(cliente)
                    .estado(EstadoOrden.PENDIENTE)
                    .direccionEnvio("Av. Principal 123")
                    .regionEnvio("Región Metropolitana")
                    .comunaEnvio("Santiago")
                    .notas("Entregar en horario de oficina")
                    .subtotal(BigDecimal.valueOf(25000))
                    .descuento(BigDecimal.ZERO)
                    .total(BigDecimal.valueOf(30000))
                    .build();

            if (productos.size() > 0) {
                ItemOrden item1 = ItemOrden.builder()
                        .orden(orden1)
                        .producto(productos.get(0))
                        .nombreProducto(productos.get(0).getNombre())
                        .cantidad(2)
                        .precioUnitario(productos.get(0).getPrecio())
                        .subtotal(productos.get(0).getPrecio().multiply(BigDecimal.valueOf(2)))
                        .build();
                orden1.agregarItem(item1);
            }

            // Orden 2: Pagada
            Orden orden2 = Orden.builder()
                    .numeroOrden("ORD-002")
                    .usuario(admin)
                    .estado(EstadoOrden.PAGADA)
                    .direccionEnvio("Calle Secundaria 456")
                    .regionEnvio("Valparaíso")
                    .comunaEnvio("Valparaíso")
                    .notas("")
                    .subtotal(BigDecimal.valueOf(45000))
                    .descuento(BigDecimal.ZERO)
                    .total(BigDecimal.valueOf(50000))
                    .build();

            if (productos.size() > 1) {
                ItemOrden item2 = ItemOrden.builder()
                        .orden(orden2)
                        .producto(productos.get(1))
                        .nombreProducto(productos.get(1).getNombre())
                        .cantidad(1)
                        .precioUnitario(productos.get(1).getPrecio())
                        .subtotal(productos.get(1).getPrecio())
                        .build();
                orden2.agregarItem(item2);
            }

            // Orden 3: En Preparación
            Orden orden3 = Orden.builder()
                    .numeroOrden("ORD-003")
                    .usuario(cliente)
                    .estado(EstadoOrden.EN_PREPARACION)
                    .direccionEnvio("Pasaje Los Olivos 789")
                    .regionEnvio("Región Metropolitana")
                    .comunaEnvio("Las Condes")
                    .notas("Producto frágil, manejar con cuidado")
                    .subtotal(BigDecimal.valueOf(15000))
                    .descuento(BigDecimal.valueOf(2000))
                    .total(BigDecimal.valueOf(18000))
                    .build();

            if (productos.size() > 2) {
                ItemOrden item3 = ItemOrden.builder()
                        .orden(orden3)
                        .producto(productos.get(2))
                        .nombreProducto(productos.get(2).getNombre())
                        .cantidad(3)
                        .precioUnitario(productos.get(2).getPrecio())
                        .subtotal(productos.get(2).getPrecio().multiply(BigDecimal.valueOf(3)))
                        .build();
                orden3.agregarItem(item3);
            }

            // Orden 4: Enviada
            Orden orden4 = Orden.builder()
                    .numeroOrden("ORD-004")
                    .usuario(admin)
                    .estado(EstadoOrden.ENVIADA)
                    .direccionEnvio("Av. Costanera 321")
                    .regionEnvio("Biobío")
                    .comunaEnvio("Concepción")
                    .notas("")
                    .subtotal(BigDecimal.valueOf(60000))
                    .descuento(BigDecimal.ZERO)
                    .total(BigDecimal.valueOf(65000))
                    .build();

            if (productos.size() > 0) {
                ItemOrden item4a = ItemOrden.builder()
                        .orden(orden4)
                        .producto(productos.get(0))
                        .nombreProducto(productos.get(0).getNombre())
                        .cantidad(1)
                        .precioUnitario(productos.get(0).getPrecio())
                        .subtotal(productos.get(0).getPrecio())
                        .build();
                orden4.agregarItem(item4a);
            }
            if (productos.size() > 1) {
                ItemOrden item4b = ItemOrden.builder()
                        .orden(orden4)
                        .producto(productos.get(1))
                        .nombreProducto(productos.get(1).getNombre())
                        .cantidad(2)
                        .precioUnitario(productos.get(1).getPrecio())
                        .subtotal(productos.get(1).getPrecio().multiply(BigDecimal.valueOf(2)))
                        .build();
                orden4.agregarItem(item4b);
            }

            ordenRepository.saveAll(List.of(orden1, orden2, orden3, orden4));
            log.info("- 4 ordenes de prueba creadas");
        }
    }
}
