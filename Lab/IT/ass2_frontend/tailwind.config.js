/** @type {import('tailwindcss').Config} */
module.exports = {
  content: [
    "./views/**/*.{html,js}",
  ],
  theme: {
    extend: {
      colors: {
        brand: "#50d0ba",
      },
      fontFamily: {
        sans: ["Poppins", "sans-serif"],
        poppins: ["Poppins", "sans-serif"],
        courgette: ["Courgette", "cursive"],
      },
    },
  },
  plugins: [
    require('@tailwindcss/forms')({
      strategy: "base"
    }),
  ]
}
