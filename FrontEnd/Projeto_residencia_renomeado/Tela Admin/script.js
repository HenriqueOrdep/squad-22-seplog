 
            // Gerar estrelas para o fundo
            document.addEventListener('DOMContentLoaded', function() {
                const spaceBg = document.getElementById('space-bg');
                const starCount = 150;
                
                for (let i = 0; i < starCount; i++) {
                    const star = document.createElement('div');
                    star.className = 'star';
                    
                    // Tamanho aleatório
                    const size = Math.random() * 3;
                    star.style.width = `${size}px`;
                    star.style.height = `${size}px`;
                    
                    // Posição aleatória
                    star.style.left = `${Math.random() * 100}%`;
                    star.style.top = `${Math.random() * 100}%`;
                    
                    // Atraso na animação
                    star.style.animationDelay = `${Math.random() * 5}s`;
                    
                    spaceBg.appendChild(star);
                }
                
                // Dados de exemplo para a tabela
                const users = [
                    { id: 1, name: 'Ana Silva', service: 'Procon', phone: '(79) 99123-4567', email: 'ana.silva@email.com', neighborhood: 'Centro', status: 'active' },
                    { id: 2, name: 'João Oliveira', service: 'Saúde', phone: '(79) 98765-4321', email: 'joao.oliveira@email.com', neighborhood: 'Atalaia', status: 'active' },
                    { id: 3, name: 'Maria Santos', service: 'Educação', phone: '(79) 99876-5432', email: 'maria.santos@email.com', neighborhood: 'Jardins', status: 'inactive' },
                    { id: 4, name: 'Pedro Costa', service: 'Meio Ambiente', phone: '(79) 98234-5678', email: 'pedro.costa@email.com', neighborhood: 'Farolândia', status: 'active' },
                    { id: 5, name: 'Carla Mendes', service: 'Procon', phone: '(79) 99345-6789', email: 'carla.mendes@email.com', neighborhood: 'Salgado Filho', status: 'active' },
                    { id: 6, name: 'Lucas Ferreira', service: 'Saúde', phone: '(79) 98456-7890', email: 'lucas.ferreira@email.com', neighborhood: 'São José', status: 'inactive' },
                    { id: 7, name: 'Juliana Lima', service: 'Educação', phone: '(79) 99567-8901', email: 'juliana.lima@email.com', neighborhood: 'Luzia', status: 'active' },
                    { id: 8, name: 'Roberto Alves', service: 'Meio Ambiente', phone: '(79) 98678-9012', email: 'roberto.alves@email.com', neighborhood: 'Grageru', status: 'active' },
                    { id: 9, name: 'Fernanda Dias', service: 'Procon', phone: '(79) 99789-0123', email: 'fernanda.dias@email.com', neighborhood: '13 de Julho', status: 'inactive' },
                    { id: 10, name: 'Marcelo Gomes', service: 'Saúde', phone: '(79) 98890-1234', email: 'marcelo.gomes@email.com', neighborhood: 'Inácio Barbosa', status: 'active' }
                ];
                
                // Preencher a tabela
                populateTable(users);
                
                // Sidebar mobile toggle
                const mobileMenuButton = document.getElementById('mobile-menu-button');
                const sidebar = document.getElementById('sidebar');
                const sidebarOverlay = document.getElementById('sidebar-overlay');
                const contentArea = document.getElementById('content-area');
                
                mobileMenuButton.addEventListener('click', toggleSidebar);
                sidebarOverlay.addEventListener('click', toggleSidebar);
                
                function toggleSidebar() {
                    sidebar.classList.toggle('-translate-x-full');
                    sidebarOverlay.classList.toggle('hidden');
                    document.body.classList.toggle('overflow-hidden');
                }
                
                // Responsividade da sidebar
                function handleResize() {
                    if (window.innerWidth < 1024) {
                        sidebar.classList.add('-translate-x-full');
                        contentArea.classList.remove('ml-64');
                        contentArea.classList.add('ml-0');
                    } else {
                        sidebar.classList.remove('-translate-x-full');
                        contentArea.classList.remove('ml-0');
                        contentArea.classList.add('ml-64');
                        sidebarOverlay.classList.add('hidden');
                    }
                }
                
                window.addEventListener('resize', handleResize);
                handleResize();
                
                // Filtro da tabela
                const tableSearch = document.getElementById('table-search');
                const statusFilter = document.getElementById('status-filter');
                
                tableSearch.addEventListener('input', filterTable);
                statusFilter.addEventListener('change', filterTable);
                
                function filterTable() {
                    const searchTerm = tableSearch.value.toLowerCase();
                    const statusValue = statusFilter.value;
                    
                    const filteredUsers = users.filter(user => {
                        const matchesSearch = 
                            user.name.toLowerCase().includes(searchTerm) ||
                            user.email.toLowerCase().includes(searchTerm) ||
                            user.service.toLowerCase().includes(searchTerm) ||
                            user.neighborhood.toLowerCase().includes(searchTerm);
                        
                        const matchesStatus = 
                            statusValue === 'all' || 
                            user.status === statusValue;
                        
                        return matchesSearch && matchesStatus;
                    });
                    
                    populateTable(filteredUsers);
                    
                    // Mostrar estado vazio se não houver resultados
                    const emptyState = document.getElementById('empty-state');
                    if (filteredUsers.length === 0) {
                        emptyState.classList.remove('hidden');
                    } else {
                        emptyState.classList.add('hidden');
                    }
                    
                    // Atualizar contador de registros
                    document.getElementById('showing-start').textContent = filteredUsers.length > 0 ? 1 : 0;
                    document.getElementById('showing-end').textContent = filteredUsers.length;
                    document.getElementById('total-items').textContent = users.length;
                }
                
                // Modal de adicionar usuário
                const addUserBtn = document.getElementById('add-user-btn');
                const addUserModal = document.getElementById('add-user-modal');
                const closeModal = document.getElementById('close-modal');
                const cancelAddUser = document.getElementById('cancel-add-user');
                const addUserForm = document.getElementById('add-user-form');
                
                addUserBtn.addEventListener('click', () => {
                    addUserModal.classList.add('show');
                });
                
                closeModal.addEventListener('click', () => {
                    addUserModal.classList.remove('show');
                });
                
                cancelAddUser.addEventListener('click', () => {
                    addUserModal.classList.remove('show');
                });
                
                addUserForm.addEventListener('submit', (e) => {
                    e.preventDefault();
                    
                    // Obter valores do formulário
                    const name = document.getElementById('name').value;
                    const service = document.getElementById('service').value;
                    const phone = document.getElementById('phone').value;
                    const email = document.getElementById('email').value;
                    const neighborhood = document.getElementById('neighborhood').value;
                    const status = document.getElementById('status').value;
                    
                    // Adicionar novo usuário
                    const newUser = {
                        id: users.length + 1,
                        name,
                        service,
                        phone,
                        email,
                        neighborhood,
                        status
                    };
                    
                    users.unshift(newUser);
                    populateTable(users);
                    
                    // Fechar modal
                    addUserModal.classList.remove('show');
                    
                    // Resetar formulário
                    addUserForm.reset();
                    
                    // Mostrar toast
                    showToast('Usuário adicionado com sucesso!');
                });
                
                // Toast notification
                function showToast(message) {
                    const toast = document.getElementById('toast');
                    const toastMessage = document.getElementById('toast-message');
                    
                    toastMessage.textContent = message;
                    toast.classList.add('show');
                    
                    setTimeout(() => {
                        toast.classList.remove('show');
                    }, 3000);
                }
                
                // Paginação
                const paginationItems = document.querySelectorAll('.pagination-item');
                paginationItems.forEach(item => {
                    item.addEventListener('click', function() {
                        if (!this.classList.contains('active') && !this.querySelector('i')) {
                            paginationItems.forEach(i => i.classList.remove('active'));
                            this.classList.add('active');
                            
                            // Simular carregamento
                            const tableBody = document.getElementById('users-table-body');
                            const loadingSkeleton = document.getElementById('loading-skeleton');
                            
                            tableBody.classList.add('hidden');
                            loadingSkeleton.classList.remove('hidden');
                            
                            setTimeout(() => {
                                loadingSkeleton.classList.add('hidden');
                                tableBody.classList.remove('hidden');
                                
                                // Atualizar contador
                                const page = parseInt(this.textContent);
                                document.getElementById('showing-start').textContent = (page - 1) * 10 + 1;
                                document.getElementById('showing-end').textContent = Math.min(page * 10, users.length);
                            }, 500);
                        }
                    });
                });
                
                // Função para preencher a tabela
                function populateTable(data) {
                    const tableBody = document.getElementById('users-table-body');
                    tableBody.innerHTML = '';
                    
                    data.forEach(user => {
                        const row = document.createElement('tr');
                        row.className = 'border-b border-gray-700';
                        
                        row.innerHTML = `
                            <td class="px-4 py-3 text-sm">
                                <div class="flex items-center">
                                    <div class="w-8 h-8 rounded-full bg-purple-600 flex items-center justify-center text-xs font-bold mr-3">
                                        ${user.name.split(' ').map(n => n[0]).join('')}
                                    </div>
                                    <span>${user.name}</span>
                                </div>
                            </td>
                            <td class="px-4 py-3 text-sm">
                                <span class="px-2 py-1 rounded-md bg-opacity-20 ${getServiceColor(user.service)}">
                                    ${user.service}
                                </span>
                            </td>
                            <td class="px-4 py-3 text-sm">${user.phone}</td>
                            <td class="px-4 py-3 text-sm">${user.email}</td>
                            <td class="px-4 py-3 text-sm">${user.neighborhood}</td>
                            <td class="px-4 py-3 text-sm">
                                <span class="status-badge ${user.status === 'active' ? 'status-active' : 'status-inactive'}">
                                    ${user.status === 'active' ? 'Ativo' : 'Inativo'}
                                </span>
                            </td>
                            <td class="px-4 py-3 text-sm text-right">
                                <div class="flex justify-end space-x-2">
                                    <button class="text-blue-400 hover:text-blue-300" aria-label="Editar">
                                        <i class="fas fa-edit"></i>
                                    </button>
                                    <button class="text-red-400 hover:text-red-300" aria-label="Excluir">
                                        <i class="fas fa-trash-alt"></i>
                                    </button>
                                </div>
                            </td>
                        `;
                        
                        tableBody.appendChild(row);
                    });
                    
                    // Adicionar eventos aos botões de ação
                    const editButtons = document.querySelectorAll('.fa-edit');
                    const deleteButtons = document.querySelectorAll('.fa-trash-alt');
                    
                    editButtons.forEach(button => {
                        button.addEventListener('click', function() {
                            showToast('Função de edição em desenvolvimento');
                        });
                    });
                    
                    deleteButtons.forEach(button => {
                        button.addEventListener('click', function() {
                            const row = this.closest('tr');
                            row.classList.add('bg-red-900', 'bg-opacity-20');
                            
                            setTimeout(() => {
                                row.style.transition = 'all 0.5s ease';
                                row.style.opacity = '0';
                                row.style.height = '0';
                                row.style.padding = '0';
                                
                                setTimeout(() => {
                                    row.remove();
                                    showToast('Usuário removido com sucesso!');
                                }, 500);
                            }, 300);
                        });
                    });
                    
                    // Adicionar eventos para tags clicáveis
                    const serviceTags = document.querySelectorAll('td:nth-child(2) span');
                    serviceTags.forEach(tag => {
                        tag.style.cursor = 'pointer';
                        tag.addEventListener('click', function() {
                            const service = this.textContent.trim();
                            statusFilter.value = 'all';
                            tableSearch.value = service;
                            filterTable();
                            showToast(`Filtrado por serviço: ${service}`);
                        });
                    });
                }
                
                // Função para obter cor baseada no serviço
                function getServiceColor(service) {
                    switch(service) {
                        case 'Procon':
                            return 'bg-blue-600 text-blue-200';
                        case 'Saúde':
                            return 'bg-red-600 text-red-200';
                        case 'Educação':
                            return 'bg-yellow-600 text-yellow-200';
                        case 'Meio Ambiente':
                            return 'bg-green-600 text-green-200';
                        default:
                            return 'bg-gray-600 text-gray-200';
                    }
                }
            });
     
   