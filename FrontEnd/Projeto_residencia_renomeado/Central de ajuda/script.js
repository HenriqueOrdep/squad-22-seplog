  
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
    
        // Mobile menu toggle
        const mobileMenuButton = document.getElementById('mobile-menu-button');
        const mobileMenu = document.getElementById('mobile-menu');
        
        mobileMenuButton.addEventListener('click', () => {
            mobileMenu.classList.toggle('hidden');
        });
        
        // FAQ accordion functionality
        const faqQuestions = document.querySelectorAll('.faq-question');
        
        faqQuestions.forEach(question => {
            question.addEventListener('click', () => {
                const faqItem = question.parentElement;
                
                // Close all other FAQ items
                document.querySelectorAll('.faq-item').forEach(item => {
                    if (item !== faqItem) {
                        item.classList.remove('active');
                    }
                });
                
                // Toggle current FAQ item
                faqItem.classList.toggle('active');
            });
        });
        
        // Search input functionality
        const searchInput = document.querySelector('.search-input');
        const searchSuggestions = document.querySelector('.search-suggestions');
        
        searchInput.addEventListener('focus', () => {
            searchSuggestions.style.display = 'block';
        });
        
        searchInput.addEventListener('blur', (e) => {
            // Delay hiding to allow clicking on suggestions
            setTimeout(() => {
                if (!searchSuggestions.contains(document.activeElement)) {
                    searchSuggestions.style.display = 'none';
                }
            }, 100);
        });
        
        // Click on search suggestion
        document.querySelectorAll('.search-suggestions li').forEach(item => {
            item.addEventListener('click', () => {
                searchInput.value = item.querySelector('span').textContent;
                searchSuggestions.style.display = 'none';
            });
        });
        
        // Category cards click
        document.querySelectorAll('.category-card').forEach(card => {
            card.addEventListener('click', () => {
                // In a real app, this would navigate to the category page
                const categoryName = card.querySelector('h3').textContent;
                console.log(`Navigating to category: ${categoryName}`);
            });
        });
        
        // Feedback buttons
        const feedbackButtons = document.querySelectorAll('.feedback-section button');
        feedbackButtons.forEach(button => {
            button.addEventListener('click', () => {
                // In a real app, this would send feedback to the server
                const feedback = button.textContent.includes('Sim') ? 'positive' : 'negative';
                console.log(`User provided ${feedback} feedback`);
                
                // Show thank you message
                const feedbackSection = button.closest('.feedback-section');
                feedbackSection.innerHTML = '<p class="text-green-600">Obrigado pelo seu feedback!</p>';
            });
        });
 