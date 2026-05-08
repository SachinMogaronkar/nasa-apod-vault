const API_BASE =
    window.location.hostname === "localhost"
        ? "http://localhost:5000"
        : "https://nasa-apod-vault.onrender.com";

const NASA_API = `${API_BASE}/api/nasa`;

// ================= AUTH =================
(function () {

    const token = localStorage.getItem("token");

    const path = window.location.pathname;

    const isLogin =
        path === "/" ||
        path.includes("index.html");

    if (!token && !isLogin) {
        window.location.href = "/";
    }

    if (token && isLogin) {
        window.location.href = "Homepage.html";
    }

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

function authHeaders() {
    return {
        "Authorization": "Bearer " + getToken()
    };
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

        const res = await fetch(`${NASA_API}/apod`, {
            method: "GET",
            headers: authHeaders()
        });

        if (!res.ok) {
            throw new Error(`HTTP ${res.status}`);
        }

        const data = await res.json();

        render(data);

    } catch (e) {

        console.error("APOD LOAD ERROR:", e);

        setError("Failed to load APOD");
    }
}

// ================= BY DATE =================
async function loadApodByDate() {

    const date = document.getElementById("apodDate").value;

    if (!date) return;

    setLoading();

    try {

        const res = await fetch(
            `${NASA_API}/apod/date?date=${date}`,
            {
                method: "GET",
                headers: authHeaders()
            }
        );

        if (!res.ok) {
            throw new Error(`HTTP ${res.status}`);
        }

        const data = await res.json();

        render(data);

    } catch (e) {

        console.error("DATE APOD ERROR:", e);

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

        let url;
        if (currentApod.date) {

            url =
                `${NASA_API}/apod/save?date=${currentApod.date}`;
        }
        
        else {

            url = `${NASA_API}/apod/save/today`;
        }

        const res = await fetch(url, {
            method: "POST",
            headers: authHeaders()
        });

        if (!res.ok) {
            throw new Error(`HTTP ${res.status}`);
        }

        alert("Saved successfully!");

    } catch (e) {

        console.error("SAVE ERROR:", e);

        alert("Save failed");
    }
}

// ================= SAVED =================
async function showSaved() {

    const container =
        document.getElementById("savedContainer");

    document.querySelector(".carousel").style.display =
        "none";

    container.style.display = "grid";

    container.innerHTML = "Loading...";

    try {

        const res = await fetch(
            `${NASA_API}/apod/saved`,
            {
                method: "GET",
                headers: authHeaders()
            }
        );

        if (!res.ok) {
            throw new Error(`HTTP ${res.status}`);
        }

        const data = await res.json();

        if (!Array.isArray(data) || data.length === 0) {

            container.innerHTML =
                "<p>No saved APODs</p>";

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

            del.onclick = () =>
                openDeleteModal(item.id);

            card.appendChild(img);
            card.appendChild(del);
            card.appendChild(title);

            container.appendChild(card);
        });

    } catch (e) {

        console.error("SAVED ERROR:", e);

        container.innerHTML = "Failed to load";
    }
}

// ================= VIEW SAVED =================
function renderSaved(item) {

    showArrows();

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

// ================= KEYBOARD =================
document.addEventListener("keydown", (e) => {

    if (e.key === "ArrowRight") {
        nextSaved();
    }

    if (e.key === "ArrowLeft") {
        prevSaved();
    }
});

// ================= SWITCH =================
function showToday() {
    loadApod();
}

// ================= DELETE =================
function openDeleteModal(id) {

    deleteId = id;

    document.getElementById("deleteModal").style.display =
        "flex";
}

function closeModal() {

    deleteId = null;

    document.getElementById("deleteModal").style.display =
        "none";
}

async function confirmDelete() {

    if (!deleteId) return;

    try {

        const res = await fetch(
            `${NASA_API}/apod/${deleteId}`,
            {
                method: "DELETE",
                headers: authHeaders()
            }
        );

        if (!res.ok) {
            throw new Error(`HTTP ${res.status}`);
        }

        closeModal();

        showSaved();

    } catch (e) {

        console.error("DELETE ERROR:", e);

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

    window.location.href = "/";
}