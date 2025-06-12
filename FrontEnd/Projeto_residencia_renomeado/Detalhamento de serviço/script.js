        // Mobile menu toggle
        document.querySelector('button.md\\:hidden').addEventListener('click', function() {
            const nav = document.querySelector('nav.hidden');
            if (nav.classList.contains('hidden')) {
                nav.classList.remove('hidden');
                nav.classList.add('flex', 'flex-col', 'absolute', 'top-16', 'right-4', 'bg-primary-700', 'p-4', 'rounded-lg', 'shadow-lg', 'space-y-3', 'z-50');
            } else {
                nav.classList.add('hidden');
                nav.classList.remove('flex', 'flex-col', 'absolute', 'top-16', 'right-4', 'bg-primary-700', 'p-4', 'rounded-lg', 'shadow-lg', 'space-y-3', 'z-50');
            }
        });

        // Iniciar solicitação button
        document.querySelector('button.bg-secondary-600').addEventListener('click', function() {
            alert('Você será redirecionado para o formulário de solicitação após fazer login.');
        });

        tailwind.config = {
            theme: {
                extend: {
                    colors: {
                        primary: {
                            100: '#e0e7ff',
                            200: '#c7d2fe',
                            300: '#a5b4fc',
                            400: '#818cf8',
                            500: '#6366f1',
                            600: '#4f46e5',
                            700: '#4338ca',
                            800: '#3730a3',
                            900: '#312e81',
                        },
                        secondary: {
                            100: '#ede9fe',
                            300: '#c4b5fd',
                            500: '#8b5cf6',
                            700: '#6d28d9',
                            900: '#4c1d95',
                        },
                    },
                    fontFamily: {
                        sans: ['Inter', 'sans-serif'],
                    },
                }
            }
        }