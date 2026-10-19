module.exports = {
  content: [
    "./src/**/*.{js,jsx,ts,tsx}",
  ],
  theme: {
    extend: {
      colors: {
        primary: {
          DEFAULT: "#3b82f6",
          hover: "#2563eb",
        },
        background: {
          DEFAULT: "#ffffff",
          card: "#f9fafb",
        },
        text: {
          DEFAULT: "#1f2937",
          light: "#6b7280",
        },
        border: "#e5e7eb",
      },
      fontSize: {
        xs: "0.75rem",
        sm: "0.875rem",
        base: "1rem",
        lg: "1.125rem",
        xl: "1.25rem",
        xxl: "1.5rem",
      },
      spacing: {
        auto: "auto",
        1: "1rem",
        2: "1.5rem",
        4: "2rem",
        8: "3rem",
        16: "4rem",
        32: "8rem",
      },
      borderRadius: {
        none: "0",
        sm: "0.125rem",
        md: "0.25rem",
        lg: "0.5rem",
        full: "9999px",
      },
    },
  },
  plugins: []
}