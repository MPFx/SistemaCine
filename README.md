# Sistema de Cine

Sistema de reservas de cine desarrollado en Java. Permite consultar cartelera, reservar asientos, gestionar clientes y generar tickets.

## Documentación

Puedes ver el Javadoc aquí:  
[https://mpfx.github.io/SistemaCine/](https://mpfx.github.io/SistemaCine/)

## Funcionalidades

- **Clientes**: Registro, inicio de sesión, perfil, niveles de membresía (Básica, Plata, Oro, Platino)
- **Cartelera**: Ver películas, funciones disponibles, horarios, salas
- **Reservas**: Selección de asientos (Normal, VIP, Premium), aplicación de descuentos, pago
- **Puntos**: Acumulación de puntos por compras (1 punto por cada $1,000)
- **Tickets**: Generación de ticket con código QR simulado

## Tipos de Asiento

| Tipo | Características | Precio |
|------|----------------|--------|
| Normal | Estándar | Base |
| VIP | Bebida + comida incluida | 2.5x base + $8,000 |
| Premium | Reclinable, calefacción | 4.0x base + $2,000 |

## Niveles de Membresía

| Nivel | Puntos necesarios | Descuento |
|-------|-------------------|-----------|
| Básica | 0 | 0% |
| Plata | 501 | 5% |
| Oro | 2,001 | 10% |
| Platino | 5,001+ | 15% |

## Tecnologías

- Java
- Javadoc
- GitHub Pages

## Derechos de autor
**© 2026 ISC Israel de Jesus Mar Parada (MPFx++)**

Todos los derechos reservados.


## Alcance

### ✅ Qué hace
- Registro e inicio de sesión de clientes
- Visualización de cartelera y funciones disponibles
- Selección de asientos (Normal, VIP, Premium)
- Aplicación de descuentos por niveles de membresía
- Acumulación de puntos por compras
- Generación de tickets con código QR simulado

### ❌ Qué NO hace
- No tiene interfaz gráfica (solo consola)
- No persiste datos en base de datos real (usa DataStore en memoria)
- No procesa pagos reales
- No envía correos electrónicos reales
- No genera códigos QR funcionales (solo simulación)

## Documentación Javadoc
[https://mpfx.github.io/SistemaCine/](https://mpfx.github.io/SistemaCine/)

## Modo de uso
**Este proyecto NO es una aplicación visual/gráfica.**
Funciona exclusivamente por consola (CLI - Command Line Interface).

## Propósito
**Educativo / Pedagógico**
Este proyecto fue desarrollado con fines de aprendizaje y práctica de:
- Programación orientada a objetos en Java
- Documentación técnica con Javadoc
- Control de versiones con Git y GitHub Pages

Este software se proporciona "tal cual", sin garantías de ningún tipo. 
No está permitida su reproducción, distribución o modificación sin autorización expresa del autor.
