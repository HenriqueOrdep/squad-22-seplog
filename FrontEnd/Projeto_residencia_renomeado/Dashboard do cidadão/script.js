
        tailwind.config = {
            theme: {
                extend: {
                    colors: {
                        primary: {
                            light: '#8B5CF6',
                            DEFAULT: '#7C3AED',
                            dark: '#6D28D9',
                        },
                        secondary: {
                            light: '#60A5FA',
                            DEFAULT: '#3B82F6',
                            dark: '#2563EB',
                        },
                        tertiary: {
                            light: '#C4B5FD',
                            DEFAULT: '#A78BFA',
                            dark: '#8B5CF6',
                        },
                    },
                    fontFamily: {
                        sans: ['Inter', 'sans-serif'],
                    },
                }
            }
        }
    
        // Toggle menu items
        document.querySelectorAll('.menu-item').forEach(item => {
            item.addEventListener('click', function() {
                document.querySelectorAll('.menu-item').forEach(el => {
                    el.classList.remove('active');
                });
                this.classList.add('active');
            });
        });
        
        // Responsive behavior for mobile
        function checkScreenSize() {
            const sidebar = document.querySelector('.sidebar');
            const mainContent = document.querySelector('.main-content');
            
            if (window.innerWidth <= 768) {
                mainContent.classList.remove('ml-64');
                mainContent.classList.add('ml-0');
            } else {
                mainContent.classList.remove('ml-0');
                mainContent.classList.add('ml-64');
            }
        }
        
        // Check on load and resize
        window.addEventListener('load', checkScreenSize);
        window.addEventListener('resize', checkScreenSize);
    