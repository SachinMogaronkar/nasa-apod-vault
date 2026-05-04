const BASE_URL = "http://localhost:5000/api/nasa";

// ================= AUTH =================
(function () {
    const token = localStorage.getItem("token");
    const isLogin = window.location.pathname.includes("Login.html");

    if (!token && !isLogin) window.location.href = "Login.html";
    if (token && isLogin) window.location.href = "Homepage.html";
})();

// ================= STATE =================
let savedList = [];
let currentIndex = -1;
let currentApod = null;
let deleteId = null;

// ================= HELPERS =================
function getToken() {
    return localStorage.getItem("token");
}

function showArrows() {
    document.getElementById("navArrows").style.display = "flex";
}

function hideArrows() {
    document.getElementById("navArrows").style.display = "none";
}

function setLoading() {
    const img = document.getElementById("image");
    img.style.opacity = 0;
    img.src = "";

    document.getElementById("description").innerHTML =
        "<p>Loading...</p>";
}

function setError(msg) {
    document.getElementById("description").innerHTML =
        `<p style="color:red;">${msg}</p>`;
}

// ================= RENDER =================
function render(data, fromSaved = false) {

    currentApod = data;

    if (fromSaved) {
        showArrows();
    } else {
        hideArrows();
    }

    const img = document.getElementById("image");

    document.querySelector(".carousel").style.display = "flex";
    document.getElementById("savedContainer").style.display = "none";

    img.style.opacity = 0;

    const temp = new Image();

    temp.onload = () => {
        img.src = temp.src;
        img.style.transition = "opacity 0.4s ease";
        img.style.opacity = 1;
    };

    temp.onerror = () => setError("Image failed");

    temp.src = data.url;

    document.getElementById("description").innerHTML = `
        <strong class="place-name">${data.title}</strong>
        <p>${data.explanation}</p>
    `;
}

// ================= TODAY =================
async function loadApod() {

    setLoading();

    try {
        const res = await fetch(`${BASE_URL}/apod`, {
            headers: {
                "Authorization": "Bearer " + getToken()
            }
        });

        if (!res.ok) throw new Error();

        const data = await res.json();
        render(data);

    } catch {
        setError("Failed to load APOD");
    }
}

// ================= BY DATE =================
async function loadApodByDate() {

    const date = document.getElementById("apodDate").value;
    if (!date) return;

    setLoading();

    try {
        const res = await fetch(`${BASE_URL}/apod/date?date=${date}`, {
            headers: {
                "Authorization": "Bearer " + getToken()
            }
        });

        if (!res.ok) throw new Error();

        const data = await res.json();
        render(data);

    } catch {
        setError("Failed to load APOD");
    }
}

// ================= DATE NAV =================
function prevDay() {
    const input = document.getElementById("apodDate");
    if (!input.value) return;

    let date = new Date(input.value);
    date.setDate(date.getDate() - 1);
    updateDate(date);
}

function nextDay() {
    const input = document.getElementById("apodDate");
    if (!input.value) return;

    let date = new Date(input.value);
    date.setDate(date.getDate() + 1);

    if (date > new Date()) return;

    updateDate(date);
}

function updateDate(date) {
    const d = date.toISOString().split("T")[0];
    document.getElementById("apodDate").value = d;
    loadApodByDate();
}

// ================= SAVE =================
async function saveToday() {

    if (!currentApod) {
        alert("Nothing to save");
        return;
    }

    try {
        let url = `${BASE_URL}/apod/save/today`;

        if (currentApod.date) {
            url += `?date=${currentApod.date}`;
        }

        const res = await fetch(url, {
            method: "POST",
            headers: {
                "Authorization": "Bearer " + getToken()
            }
        });

        if (!res.ok) throw new Error();

        alert("Saved successfully!");

    } catch (e) {
        console.error(e);
        alert("Save failed");
    }
}

// ================= SAVED =================
async function showSaved() {

    const container = document.getElementById("savedContainer");

    document.querySelector(".carousel").style.display = "none";
    container.style.display = "grid";

    container.innerHTML = "Loading...";

    try {
        const res = await fetch(`${BASE_URL}/apod/saved`, {
            headers: {
                "Authorization": "Bearer " + getToken()
            }
        });

        const data = await res.json();

        if (!Array.isArray(data) || data.length === 0) {
            container.innerHTML = "<p>No saved APODs</p>";
            return;
        }

        savedList = data;
        container.innerHTML = "";

        data.forEach((item, index) => {

            const card = document.createElement("div");
            card.className = "saved-card";

            const img = document.createElement("img");
            img.src = item.url;

            img.onclick = () => {
                currentIndex = index;
                renderSaved(item);

                document.querySelectorAll(".saved-card")
                    .forEach(c => c.classList.remove("active"));

                card.classList.add("active");
            };

            const title = document.createElement("p");
            title.innerText = item.title;

            const del = document.createElement("button");
            del.innerText = "🗑";
            del.onclick = () => openDeleteModal(item.id);

            card.appendChild(img);
            card.appendChild(del);
            card.appendChild(title);

            container.appendChild(card);
        });

    } catch {
        container.innerHTML = "Failed to load";
    }
}

// ================= VIEW SAVED =================
function renderSaved(item) {
    showArrows(); // 🔥 ONLY here
    render(item, true);
}

// ================= NAVIGATION =================
function nextSaved() {
    if (currentIndex < savedList.length - 1) {
        currentIndex++;
        renderSaved(savedList[currentIndex]);
    }
}

function prevSaved() {
    if (currentIndex > 0) {
        currentIndex--;
        renderSaved(savedList[currentIndex]);
    }
}

// keyboard navigation
document.addEventListener("keydown", (e) => {
    if (e.key === "ArrowRight") nextSaved();
    if (e.key === "ArrowLeft") prevSaved();
});

// ================= SWITCH =================
function showToday() {
    loadApod();
}

// ================= DELETE =================
function openDeleteModal(id) {
    deleteId = id;
    document.getElementById("deleteModal").style.display = "flex";
}

function closeModal() {
    deleteId = null;
    document.getElementById("deleteModal").style.display = "none";
}

async function confirmDelete() {

    if (!deleteId) return;

    try {
        const res = await fetch(`${BASE_URL}/apod/${deleteId}`, {
            method: "DELETE",
            headers: {
                "Authorization": "Bearer " + getToken()
            }
        });

        if (!res.ok) throw new Error();

        closeModal();
        showSaved();

    } catch {
        alert("Delete failed");
    }
}

// ================= ACTIVE TAB =================
function setActive(btn) {
    document.querySelectorAll(".view-group button")
        .forEach(b => b.classList.remove("active"));

    btn.classList.add("active");
}

// ================= LOGOUT =================
function logout() {
    localStorage.clear();
    window.location.href = "Login.html";
}