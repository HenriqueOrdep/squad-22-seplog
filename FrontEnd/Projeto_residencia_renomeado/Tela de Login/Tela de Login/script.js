 
        tailwind.config = {
            theme: {
                extend: {
                    colors: {
                        primary: {
                            light: '#9061F9',
                            DEFAULT: '#7C3AED',
                            dark: '#6D28D9',
                        },
                        secondary: {
                            light: '#60A5FA',
                            DEFAULT: '#3B82F6',
                            dark: '#2563EB',
                        },
                        aracaju: {
                            light: '#4FD1C5',
                            DEFAULT: '#38B2AC',
                            dark: '#319795',
                        }
                    },
                    fontFamily: {
                        sans: ['Inter', 'sans-serif'],
                    },
                }
            }
        }
    
        // Password visibility toggle
        const passwordField = document.getElementById('password');
        const passwordToggle = document.getElementById('password-toggle');
        
        passwordToggle.addEventListener('click', function() {
            const type = passwordField.getAttribute('type') === 'password' ? 'text' : 'password';
            passwordField.setAttribute('type', type);
            
            // Toggle eye icon
            const eyeIcon = passwordToggle.querySelector('i');
            eyeIcon.classList.toggle('fa-eye');
            eyeIcon.classList.toggle('fa-eye-slash');
        });
        
        // Form validation
        const loginForm = document.querySelector('form');
        loginForm.addEventListener('submit', function(e) {
            e.preventDefault();
            
            const email = document.getElementById('email').value;
            const password = document.getElementById('password').value;
            
            // Simple validation
            if (!email || !password) {
                alert('Por favor, preencha todos os campos.');
                return;
            }
            
            // Here you would normally send the form data to your server
            // For demo purposes, we'll just show a success message
            alert('Login realizado com sucesso!');
        });
        
        // Accessibility enhancement - focus first input on page load
        window.addEventListener('load', function() {
            document.getElementById('email').focus();
        });
